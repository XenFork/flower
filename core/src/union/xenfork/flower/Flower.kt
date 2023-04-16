/*
 * flower, a fun fighting game.
 * Copyright (C) 2023  XenFork Union
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package union.xenfork.flower

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import union.xenfork.flower.world.TerrainType
import union.xenfork.flower.world.World
import union.xenfork.flower.world.entity.MovementSystem
import union.xenfork.flower.world.entity.Player

/**
 * @author squid233
 * @since 0.1.0
 */
class Flower : Game() {
    lateinit var entityEngine: PooledEngine
        private set
    private lateinit var camera: OrthographicCamera
    private lateinit var worldCamera: OrthographicCamera
    private lateinit var batch: SpriteBatch
    private lateinit var shapeRenderer: ShapeRenderer
    lateinit var atlas: TextureAtlas
    var world: World? = null
        private set
    var player: Player? = null
        private set
    private var movementSystem: MovementSystem? = null

    /**
     * @author squid233
     * @since 0.1.0
     */
    companion object {
        @JvmStatic
        lateinit var instance: Flower
    }

    override fun create() {
        entityEngine = PooledEngine(10, 400, 10, 100)
        camera = OrthographicCamera()
        worldCamera = OrthographicCamera()
        batch = SpriteBatch(2400)
        shapeRenderer = ShapeRenderer()
        atlas = TextureAtlas("mapTiles.atlas")

        world = World(TerrainType.GARDEN)
        player = Player()
        movementSystem = MovementSystem()
        entityEngine.addEntity(player!!.entity)
        entityEngine.addSystem(movementSystem)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        camera.setToOrtho(false, width.toFloat(), height.toFloat())
        worldCamera.setToOrtho(false, width.toFloat(), height.toFloat())
    }

    private fun update() {
        var vx = 0f
        var vy = 0f
        if (Gdx.input.isKeyPressed(Input.Keys.W)) vy += 2f
        if (Gdx.input.isKeyPressed(Input.Keys.S)) vy -= 2f
        if (Gdx.input.isKeyPressed(Input.Keys.A)) vx -= 2f
        if (Gdx.input.isKeyPressed(Input.Keys.D)) vx += 2f
        player!!.velocity.x = vx
        player!!.velocity.y = vy
        entityEngine.update(Gdx.graphics.deltaTime)

        camera.update()
        // larger zoom is more content
        worldCamera.zoom = 1f / 64f
        worldCamera.position.x = player!!.position.x
        worldCamera.position.y = player!!.position.y
        worldCamera.update()
    }

    override fun render() {
        update()

        ScreenUtils.clear(0.247f, 0.569f, 0.8f, 1f)

        // world
        batch.projectionMatrix = worldCamera.combined
        batch.begin()
        world!!.also { world ->
            for (x in 0 until world.width) {
                for (y in 0 until world.height) {
                    batch.draw(world.terrainType.textureRegion.value, x.toFloat(), y.toFloat(), 1f, 1f)
                }
            }
        }
        batch.end()

        // player
        player!!.also { player ->
            val px = player.position.x
            val py = player.position.y
            val pSize = player.size.size
            shapeRenderer.projectionMatrix = worldCamera.combined
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            shapeRenderer.setColor(1f * 0.8f, 0.906f * 0.8f, 0.388f * 0.8f, 1f)
            shapeRenderer.circle(px, py, 0.25f * pSize, 48)
            shapeRenderer.setColor(1f, 0.906f, 0.388f, 1f)
            shapeRenderer.circle(px, py, 0.21875f * pSize, 48)
            shapeRenderer.end()
        }
        super.render()
    }

    override fun dispose() {
        super.dispose()
        entityEngine.removeAllEntities()
        entityEngine.removeAllSystems()
        entityEngine.clearPools()
        batch.dispose()
        shapeRenderer.dispose()
        atlas.dispose()
    }
}

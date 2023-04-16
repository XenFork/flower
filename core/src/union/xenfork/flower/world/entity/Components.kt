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

package union.xenfork.flower.world.entity

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.utils.Pool.Poolable

// mappers
val positionMapper: ComponentMapper<PositionComponent> = ComponentMapper.getFor(PositionComponent::class.java)
val velocityMapper: ComponentMapper<VelocityComponent> = ComponentMapper.getFor(VelocityComponent::class.java)
val rotationMapper: ComponentMapper<RotationComponent> = ComponentMapper.getFor(RotationComponent::class.java)
val sizeMapper: ComponentMapper<SizeComponent> = ComponentMapper.getFor(SizeComponent::class.java)
val basicPropertyMapper: ComponentMapper<BasicPropertyComponent> =
    ComponentMapper.getFor(BasicPropertyComponent::class.java)
val petalsMapper: ComponentMapper<PetalsComponent> = ComponentMapper.getFor(PetalsComponent::class.java)

// families
val physicsFamily: Family = Family.all(
    PositionComponent::class.java,
    VelocityComponent::class.java
).get()
val playerFamily: Family = Family.all(
    PositionComponent::class.java,
    VelocityComponent::class.java,
    SizeComponent::class.java,
    BasicPropertyComponent::class.java,
    PetalsComponent::class.java
).get()
val petalFamily: Family = Family.all(
    PositionComponent::class.java,
    VelocityComponent::class.java,
    RotationComponent::class.java,
    BasicPropertyComponent::class.java
).get()
val enemyFamily: Family = Family.all(
    PositionComponent::class.java,
    VelocityComponent::class.java,
    RotationComponent::class.java,
    SizeComponent::class.java,
    BasicPropertyComponent::class.java
).get()

/**
 * @author squid233
 * @since 0.1.0
 */
data class PositionComponent(var x: Float = 0f, var y: Float = 0f) : Component, Poolable {
    override fun reset() {
        x = 0f
        y = 0f
    }
}

/**
 * @author squid233
 * @since 0.1.0
 */
data class VelocityComponent(var x: Float = 0f, var y: Float = 0f) : Component, Poolable {
    override fun reset() {
        x = 0f
        y = 0f
    }
}

/**
 * @author squid233
 * @since 0.1.0
 */
data class RotationComponent(var rotation: Float = 0f) : Component, Poolable {
    override fun reset() {
        rotation = 0f
    }
}

/**
 * @author squid233
 * @since 0.1.0
 */
data class SizeComponent(var size: Float = 1f) : Component, Poolable {
    override fun reset() {
        size = 1f
    }
}

/**
 * @author squid233
 * @since 0.1.0
 */
data class BasicPropertyComponent(var health: Float = 0f, var damage: Float = 0f) : Component, Poolable {
    override fun reset() {
        health = 0f
        damage = 0f
    }
}

/**
 * @author squid233
 * @since 0.1.0
 */
data class PetalsComponent(val petals: MutableList<Any>, val hotBarPetals: MutableList<Any>) : Component

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

import com.badlogic.ashley.core.Entity
import union.xenfork.flower.Flower

/**
 * @author squid233
 * @since 0.1.0
 */
@JvmInline
value class Player(
    val entity: Entity = Flower.instance.entityEngine.let {
        it.createEntity()
            .add(it.createComponent(PositionComponent::class.java))
            .add(it.createComponent(VelocityComponent::class.java))
            .add(it.createComponent(SizeComponent::class.java))
            .add(it.createComponent(BasicPropertyComponent::class.java))
            .add(PetalsComponent(mutableListOf(), mutableListOf()))
    }
) {
    val position: PositionComponent
        get() = positionMapper.get(entity)
    val velocity: VelocityComponent
        get() = velocityMapper.get(entity)
    val size: SizeComponent
        get() = sizeMapper.get(entity)
    val basicProperty: BasicPropertyComponent
        get() = basicPropertyMapper.get(entity)
    val petals: PetalsComponent
        get() = petalsMapper.get(entity)
}

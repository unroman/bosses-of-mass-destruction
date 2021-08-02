package net.barribob.boss.mob.mobs.void_blossom

import net.barribob.boss.mob.ai.action.IActionWithCooldown
import net.barribob.maelstrom.general.random.WeightedRandom

class VoidBlossomMoveLogic(private val actions: Map<Byte, IActionWithCooldown>, val entity: VoidBlossomEntity, private val doBlossom: () -> Boolean) : IActionWithCooldown {
    override fun perform(): Int {
        val random = WeightedRandom<Byte>()
        val spikeWeight = 1.0
        val spikeWaveWeight = 1.0
        val sporeWeight = 1.0
        val bladeWeight = 1.0

        val moveByte = if (doBlossom()) {
            VoidBlossomAttacks.blossomAction
        } else {
            random.add(spikeWeight, VoidBlossomAttacks.spikeAttack)
            random.add(spikeWaveWeight, VoidBlossomAttacks.spikeWaveAttack)
            random.add(sporeWeight, VoidBlossomAttacks.sporeAttack)
            random.add(bladeWeight, VoidBlossomAttacks.bladeAttack)

            random.next()
        }

//        val moveByte = VoidBlossomAttacks.sporeAttack
        val action = actions[moveByte] ?: error("$moveByte action not registered as an attack")
        entity.world.sendEntityStatus(entity, moveByte)
        return action.perform()
    }
}
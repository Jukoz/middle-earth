package net.sevenstars.api.utils;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.AnimationState;

import java.util.List;

public class EntityAnimationUtil {

    /**
     * This method will play a sequence of multiple animations in order. It has to be called repeatedly (for instance in a tick-based method)
     * If the sequence is finished, animationSequenceStateIdx will be out of bounds.
     * @param animationList     A list of Pairs containing the AnimationStates and the animation's duration in ms as integer
     * @param animationSequenceStateIdx    An integer defined in the class in which this method is called to keep track of the current index in the animationList
     * @param entityAge    The age of the entity that will play this animation sequence. (Use this.age in the entities class)
     * @return The new animationSequenceStateIdx. It will only change if the current animation has finished playing.
     */
    public static int playAnimationSequence(List<Pair<AnimationState, Integer>> animationList, int animationSequenceStateIdx, int entityAge) {
        if(animationSequenceStateIdx >= animationList.size() || animationSequenceStateIdx < 0) {
            return animationSequenceStateIdx; // Index is out of bounds. This usually means that the sequence has finished playing but can also be a user error.
        }

        AnimationState previousAnimation = animationSequenceStateIdx == 0 ? null : animationList.get(animationSequenceStateIdx - 1).getFirst(); // The animation that was playing before
        AnimationState currentAnimation = animationList.get(animationSequenceStateIdx).getFirst(); // The animation that will be handled with the current animationSequenceStateIdx
        int animationDuration = animationList.get(animationSequenceStateIdx).getSecond(); // The duration of the animation that will be handled with the current animationSequenceStateIdx

        currentAnimation.startIfNotRunning(entityAge); // Start new animation
        if(previousAnimation != null && previousAnimation.isRunning()) {
            previousAnimation.stop(); // Stop old animation
        }

        return (currentAnimation.getTimeInMilliseconds(entityAge) > animationDuration) ? ++animationSequenceStateIdx : animationSequenceStateIdx;
    }

    /**
     * Checks if any animation in a sequence is running
     * @param animationList     A list of Pairs containing the AnimationStates and the animation's duration in ms as integer
     * @return true if the sequence is running, false if not
     */
    public static boolean isSequenceRunning(List<Pair<AnimationState, Integer>> animationList) {
        boolean isRunning = false;
        for(Pair<AnimationState, Integer> animationPair : animationList)
            isRunning = animationPair.getFirst().isRunning();

        return isRunning;
    }

    /**
     * Stops every animation in a sequence
     * @param animationList     A list of Pairs containing the AnimationStates and the animation's duration in ms as integer
     */
    public static void stopSequence(List<Pair<AnimationState, Integer>> animationList) {
        for(Pair<AnimationState, Integer> animationPair : animationList)
            animationPair.getFirst().stop();
    }

    /**
     * Stops an animation if its runtime has passed.
     * @param animationState    The animationState field
     * @param animatonDuration  The duration of the animation in ms
     * @param entityAge     The age of the entity that will play this animation sequence. (Use this.age in the entities class)
     */
    public static void stopIfFinished(AnimationState animationState, int animatonDuration, int entityAge) {
        if(animationState.getTimeInMilliseconds(entityAge) > animatonDuration) {
            animationState.stop();
        }
    }
}

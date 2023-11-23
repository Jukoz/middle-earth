package net.jukoz.me.entity.humanoid_npc;

public enum HumanoidNpcEmotionStates {
    Idle(0,0),
    Discussing(1,8),
    Fearful(2,16),
    Sleeping(3,24),
    Hurt(4,32);

    private int xOffset;
    private int order;
    HumanoidNpcEmotionStates(int order, int xOffset){
        this.order = order;
        this.xOffset = xOffset;
    }

    public int getXOffset() {
        return this.xOffset;
    }
    public int getOrder() {
        return this.order;
    }

}

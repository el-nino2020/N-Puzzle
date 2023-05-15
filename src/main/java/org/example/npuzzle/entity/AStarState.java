package org.example.npuzzle.entity;


import lombok.Getter;
import lombok.Setter;

/**
 * 用于 A star 算法的 State
 */
@Getter
@Setter
public class AStarState extends State {

    public AStarState(State state) {
        super(state.getGrid(), state.getParent(), state.getLastDirection(), state.getPoint());
    }

    public AStarState(State state, int g, int h) {
        this(state);
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    /**
     * f = g + h
     */
    private int f;

    /**
     * cost from initial state to this state
     */
    private int g;

    /**
     * heuristic value
     */
    private int h;

    /**
     * @see State#fork()
     */
    @Override
    public AStarState fork() {
        State fork = super.fork();
        AStarState ans = new AStarState(fork);

        ans.f = this.f;
        ans.h = this.h;
        ans.g = this.g;


        return ans;
    }

    public void recalculateF() {
        this.f = this.g + this.h;
    }
}

package com.celavin.badmintonepic.model.dto;

import com.celavin.badmintonepic.model.entity.Player;
//重修改用树结构
public class MatchNode {

    private String matchId;
    private String roundName;

    private Player p1;
    private Player p2;
    private Player winner;
    private Player loser;//需要吗?
    private RawMatchResult result;//结束后填充;

    private MatchNode nextMatch;  // 胜者去哪：父节点（下一轮）
    private MatchNode prevMatch1; // p1 怎么来的：左子节点（前置比赛1）
    private MatchNode prevMatch2; // p2 怎么来的：右子节点（前置比赛2）

    public MatchNode(String roundName) {
        this.roundName = roundName;
    }

    // 核心状态判断：这场比赛现在能打了吗？(双方落位，且还没打过)
    //TODO 暂时不处理轮空
    public boolean isPlayable() {
        return p1 != null && p2 != null && winner == null;
    }

    // 核心状态判断：这场比赛打完了吗？
    public boolean isPlayed() {
        return winner != null;
    }

    public void pushWinnerToNext(){
        if (nextMatch == null) {
            // 如果 nextMatch 为空，说明当前已经是决赛节点且直接夺冠了
            return;
        }
        if (this == nextMatch.getPrevMatch1()) {
            nextMatch.setP1(this.winner);
        } else if (this == nextMatch.getPrevMatch2()) {
            nextMatch.setP2(this.winner);
        } else {
            throw new IllegalStateException("拓扑指针断裂！当前节点不属于父节点的子节点");
        }
    }
    public void setResult(RawMatchResult result) {
        this.result = result;
        this.winner=result.getWinner();
    }



    //setget分割-------------------------------------
    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getLoser() {
        return loser;
    }

    public void setLoser(Player loser) {
        this.loser = loser;
    }

    public RawMatchResult getResult() {
        return result;
    }


    public MatchNode getNextMatch() {
        return nextMatch;
    }

    public void setNextMatch(MatchNode nextMatch) {
        this.nextMatch = nextMatch;
    }

    public MatchNode getPrevMatch1() {
        return prevMatch1;
    }

    public void setPrevMatch1(MatchNode prevMatch1) {
        this.prevMatch1 = prevMatch1;
    }

    public MatchNode getPrevMatch2() {
        return prevMatch2;
    }

    public void setPrevMatch2(MatchNode prevMatch2) {
        this.prevMatch2 = prevMatch2;
    }
}

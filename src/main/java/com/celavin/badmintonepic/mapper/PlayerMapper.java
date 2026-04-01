package com.celavin.badmintonepic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.celavin.badmintonepic.model.entity.Player;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PlayerMapper extends BaseMapper<Player> {
    @Select("select RANK() over(order by points desc) rank, * from players ")
    List<Player> getRankedPlayers();

    //重置所有人积分
    @Update("UPDATE players SET points = 1200, highest_points = 1200")
    void resetAllPointsToDefault();

    //
    @Select("select * from players " +
            "order by points desc " //+
            //"where points> "+//todo
            //"limit {nums} "
    )
    List<Player> getPlayerListByLevelAndNums(int nums);
}

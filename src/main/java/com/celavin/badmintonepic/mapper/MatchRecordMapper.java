package com.celavin.badmintonepic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.celavin.badmintonepic.model.entity.MatchRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MatchRecordMapper extends BaseMapper<MatchRecord> {

    @Select("""
            SELECT * FROM match_records
            WHERE winner_id = #{playerId} OR loser_id = #{playerId}
            ORDER BY id DESC
            LIMIT #{limit}
            """)
    List<MatchRecord> findRecentForPlayer(@Param("playerId") Long playerId, @Param("limit") int limit);
}

package com.celavin.badmintonepic.service;

import com.celavin.badmintonepic.mapper.PlayerMapper;
import com.celavin.badmintonepic.model.entity.Player;
import com.celavin.badmintonepic.service.impl.PlayerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    void getPlayerListByLevelAndNumsShouldDelegateToMapper() {
        ReflectionTestUtils.setField(playerService, "baseMapper", playerMapper);
        List<Player> expectedPlayers = List.of(createPlayer("Lin Dan"), createPlayer("Lee Chong Wei"));
        when(playerMapper.getPlayerListByLevelAndNums(5)).thenReturn(expectedPlayers);

        List<Player> actualPlayers = playerService.getPlayerListByLevelAndNums(5);

        assertSame(expectedPlayers, actualPlayers);
        verify(playerMapper).getPlayerListByLevelAndNums(5);
    }

    private Player createPlayer(String name) {
        Player player = new Player();
        player.setName(name);
        return player;
    }
}

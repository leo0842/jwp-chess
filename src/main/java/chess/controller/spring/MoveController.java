package chess.controller.spring;

import chess.dto.requestdto.MoveRequestDto;
import chess.dto.response.Response;
import chess.service.ChessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoveController {
    @Autowired
    private ChessService chessService;

    @PostMapping("/move")
    public Response<MoveRequestDto> move(@RequestBody MoveRequestDto moveRequestDto) {
        return chessService.move(moveRequestDto);
    }
}
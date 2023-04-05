package academy.tictactoe.presentation;

import academy.tictactoe.business.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller()
@RequestMapping("/levels")
public class LevelController {

    @Autowired
    public LevelController(LevelService service) {
        this.service = service;
    }

    private final LevelService service;

    @PostMapping("")
    public @ResponseBody LevelDto createNewLevel(@RequestBody LevelDto dto) {
        return this.service.createLevel(dto);
    }

    @GetMapping("")
    public List<LevelDto> getAllLevels() {
        return this.service.getAllLevels();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public @ResponseBody LevelDto getLevelById(@PathVariable Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request, id can not be null");
        }

        Optional<LevelDto> result = this.service.findLevelById(id);

        return result.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Level with id can not be found"
        ));
    }

}

package academy.tictactoe.business;

import academy.tictactoe.data.GameSetUp;
import academy.tictactoe.data.Level;
import academy.tictactoe.data.LevelRepository;
import academy.tictactoe.data.SetUpRepository;
import academy.tictactoe.presentation.LevelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LevelService {

    private final LevelRepository levelRepository;
    private final SetUpRepository setUpRepository;

    @Autowired
    public LevelService(LevelRepository levelRepository, SetUpRepository setUpRepository) {
        this.levelRepository = levelRepository;
        this.setUpRepository = setUpRepository;
    }

    @PostConstruct
    public List<LevelDto> init() {
        List<LevelDto> levels = new ArrayList<>();
        levels.add(makeEmptyGameState(new GameSetUp()));
        levels.add(makeEmptyGameState(new GameSetUp(4, 3)));
        levels.add(makeEmptyGameState(new GameSetUp(5, 3)));

        return levels;
    }

    public LevelDto makeEmptyGameState(GameSetUp setUp) {
        setUpRepository.save(setUp);

        Level level = new Level(setUp, empty(setUp));
        levelRepository.save(level);

        return LevelDto.fromEntity(level);
    }

    public static char[][] empty(GameSetUp gameSetUp) {
        int n = gameSetUp.getDimension();
        char[][] result = new char[n][n];

        for (char[] row : result) {
            Arrays.fill(row, gameSetUp.getEmptySymbol());
        }
        return result;
    }

    public Level createLevel(Level level) {
        // TODO validate fields
        GameSetUp setUp = createSetUp(level.getSetUp());
        level.setSetUp(setUp);

        Optional<Level> existingLevel = levelRepository.findOne(Example.of(level));
        return existingLevel.orElseGet(() -> levelRepository.save(level));
    }

    private GameSetUp createSetUp(GameSetUp setUp) {
        Optional<GameSetUp> existingSetUp = setUpRepository.findOne(Example.of(setUp));

        // Too good to be true - unfortunately optionals are not lazy
        //return existingSetUp.orElse(setUpRepository.save(setUp));

        return existingSetUp.orElseGet(() -> setUpRepository.save(setUp));
    }

//     Return the created dto. If it's not created - this is exceptional situation, not expected
    public LevelDto createLevel(LevelDto dto) {
        Level newLevel = LevelDto.updateEntity(new Level(), dto);

        Level result = createLevel(newLevel);

        return LevelDto.fromEntity(result);
    }

//     Return optional since it's expected, normal result that there is no such entity with the given id
    public Optional<LevelDto> findLevelById(Long id) {
        Optional<Level> level = levelRepository.findById(id);
        return level.map(LevelDto::fromEntity);
    }

    public List<LevelDto> getAllLevels() {
        return levelRepository.findAll().stream().map(LevelDto::fromEntity).collect(Collectors.toList());
    }
}

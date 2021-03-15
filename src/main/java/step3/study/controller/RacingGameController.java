package step3.study.controller;

import step3.study.domain.Drivers;
import step3.study.domain.Round;
import step3.study.dto.RequestRacingDTO;
import step3.study.dto.ResponseRacingDTO;
import step3.study.util.RandomGenerator;
import step3.study.util.StringUtils;
import step3.study.util.Validator;
import step3.study.view.InputView;
import step3.study.view.ResultView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RacingGameController {
    private final InputView inputView;
    private final ResultView resultView;
    private final Scanner scanner;
    private final static int LAST_ROUND = 1;

    public RacingGameController() {
        inputView = new InputView();
        resultView = new ResultView();
        scanner = new Scanner(System.in);
    }

    public void run() {
        RequestRacingDTO requestRacingDTO = exportRequestDtoFromInputView();
        ResponseRacingDTO responseRacingDTO = null;

        Drivers drivers = new Drivers(Drivers.of(requestRacingDTO));

        int round = requestRacingDTO.getRoundClass().getRound();
        for (int i = 0; i < round; round--) {
            responseRacingDTO = exportResponseRacingDto(drivers);
            showResultView(responseRacingDTO);
        }
        showWinner(responseRacingDTO);
    }

    private void showWinner(ResponseRacingDTO responseRacingDTO) {
        Drivers drivers = responseRacingDTO.getDrivers();
        List<String> winnerNames = drivers.getWinnerNames();
        resultView.printWinnerNames(winnerNames);
    }

    private void showResultView(ResponseRacingDTO responseRacingDTO) {
        resultView.showResultView(responseRacingDTO);
    }

    public ResponseRacingDTO exportResponseRacingDto(Drivers drivers) {
        return drivers.moveCars(new RandomGenerator(new Random()));
    }

    public RequestRacingDTO exportRequestDtoFromInputView() {
        String driverName = inputView.inputDriverNames(scanner);
        List<String> driverNames = null;
        if (Validator.isNotEmpty(driverName)) {
            driverNames = new ArrayList<>(Arrays.asList(StringUtils.split(driverName, ",")));
        }

        Round round= new Round(inputView.inputRound(scanner));

        return new RequestRacingDTO(driverNames, round);
    }
}

package com.michael.educationaltictactoe;



import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    int orientation;

    int[] xStart = {0, 2, 6, 8};

    TextView textView;
    Spinner spinnerLeft;
    Spinner spinnerRight;
    List<String> itemsLeft;
    List<String> itemsRight;
    ArrayAdapter<String> spinnerAdapterLeft;
    ArrayAdapter<String> spinnerAdapterRight;

    Spinner gameLevelSpinner;
    ArrayList<String> gameLevelArrayList;
    ArrayAdapter<String> gameLevelAdapter;
    int gameLevel = 0;

    Player playerYou;
    Player playerOpponent;

    TextView playerXScoreTextView;
    TextView playerOScoreTextView;

    TextView playerYouHeading;
    TextView playerOpponentHeading;
    TextView turnTextView;

    GridLayout gameBoard;
    ImageView imageGameBoard;
    Button resetButton;
    ImageButton exitImageButton;
    ImageButton menuImageButton;

    Button startGameButton;
    Button switchButton;

    String gameStatus;

    GridLayout gameBoardImageGrid;
    Drawable imageX;
    Drawable imageO;
    AssetManager assets;

    ImageView imageView0;
    ImageView imageView1;
    ImageView imageView2;

    String xPicName = "gold_x";
    String oPicName = "gold_o";
    boolean isTextPlayCharacter =  true;

    PopupWindow pw;
    ScrollView scrollView;
    TextView gameHeader;
    Button popupOKButton;
    RadioGroup popupRadioGroup;
    int characterCode = 2;

    int playerSelected = 0;

    int screenPixelWidth;
    int screenPixelHeight;

    DBHelper dbHelper;

    private static final String DATABASE_NAME = "TicTacToe.db";
    private static final String TABLE_NAME = "tic_tac_toe";
    private static final String GAME_LEVEL = "game_level";
    private static final String CHARACTER_CODE = "character_code";
    private static final String PLAYER_SELECTED = "player_selected";
    private static final String[] COLUMN_NAMES = {CHARACTER_CODE, GAME_LEVEL, PLAYER_SELECTED};
    private static final String[] COLUMN_DATA_TYPES = {"TINYINT", "TINYINT", "TINYINT"};
    private static final int[] INSERT_VALUES = {2, 0, 0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        screenPixelWidth = getResources().getDisplayMetrics().widthPixels;
        screenPixelHeight = getResources().getDisplayMetrics().heightPixels;

        dbHelper = new DBHelper(this, DATABASE_NAME);

        if(!dbHelper.isDatabaseExists(this, DATABASE_NAME)){

                dbHelper.createTableIfNotExists(TABLE_NAME, COLUMN_NAMES, COLUMN_DATA_TYPES);
                dbHelper.insertTableRow(TABLE_NAME, COLUMN_NAMES, INSERT_VALUES);

        }else{  characterCode = dbHelper.getTableValue(TABLE_NAME, CHARACTER_CODE, 0);
                gameLevel = dbHelper.getTableValue(TABLE_NAME, GAME_LEVEL, 0);
                playerSelected = dbHelper.getTableValue(TABLE_NAME, PLAYER_SELECTED, 0);
        }

        switch(characterCode){
            case 0: xPicName = "gold_x"; oPicName = "gold_o"; break;
            case 1: xPicName = "red_x"; oPicName = "red_o"; break;
            case 2: xPicName = "transparent"; oPicName = "transparent"; break;
            default: break;
        }

        gameBoard = (GridLayout)findViewById(R.id.gameBoardGrid);
        assets = this.getAssets();
        gameBoardImageGrid = (GridLayout) findViewById(R.id.gameBoardImageGrid);

        try {
            imageX = Drawable.createFromStream(assets.open(xPicName + ".png"), xPicName);
            imageO = Drawable.createFromStream(assets.open(oPicName + ".png"), oPicName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(xPicName.equals("transparent")) {
            isTextPlayCharacter = true;
            ColorFunctions.setTextColors(gameBoard);
        }else{
            isTextPlayCharacter = false;
        }



        gameHeader = (TextView) findViewById(R.id.gameHeader);


        orientation = getResources().getConfiguration().orientation;

        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        if(screenWidth < 1000 && orientation == Configuration.ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }



        itemsLeft = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.spinner_you)));
        itemsRight = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.spinner_items)));
        gameLevelArrayList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.game_level)));

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        playerYouHeading = (TextView) findViewById(R.id.playerXHeading);
        playerOpponentHeading = (TextView) findViewById(R.id.playerOHeading);
        turnTextView = (TextView) findViewById(R.id.turnTextView);
        playerXScoreTextView = (TextView) findViewById(R.id.playerXScoreTextView);
        playerOScoreTextView = (TextView) findViewById(R.id.playerOScoreTextView);

        spinnerLeft = (Spinner) findViewById(R.id.spinnerLeft);
        spinnerAdapterLeft = new ArrayAdapter<String>(this, R.layout.spinner_text_view_x, itemsLeft);

        spinnerRight = (Spinner) findViewById(R.id.spinnerRight);
        spinnerAdapterRight = new ArrayAdapter<String>(this, R.layout.spinner_text_view_o, itemsRight);

        gameLevelSpinner = (Spinner) findViewById(R.id.gameLevelSpinner);
        gameLevelAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_view_game_level, gameLevelArrayList);
        gameLevelSpinner.setAdapter(gameLevelAdapter);
        gameLevelSpinner.setOnItemSelectedListener(this);
        gameLevelSpinner.setBackgroundResource(R.drawable.turn);
        gameLevelSpinner.setPopupBackgroundResource(R.drawable.turn);
        gameLevelSpinner.setSelection(gameLevel);


/*
        for(int i = 0; i < 9; i++){
            ((TextView) gameBoard.getChildAt(i)).setTextColor(Color.rgb(0, 190, 205));
        }
*/
        imageGameBoard = (ImageView) findViewById(R.id.imageGameBoard);

        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(ResetButtonHandler);

        startGameButton = (Button) findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(StartGameButtonListener);

        exitImageButton = (ImageButton) findViewById(R.id.exitImageButton);
        exitImageButton.setOnClickListener(ExitImageButtonHandler);

        menuImageButton = (ImageButton) findViewById(R.id.menuImageButton);
        menuImageButton.setOnClickListener(MenuImageButtonHandler);

        switchButton = (Button) findViewById(R.id.switchButton);
        switchButton.setOnClickListener(SwitchButtonHandler);

        if(savedInstanceState == null){

            spinnerLeft.setAdapter(spinnerAdapterLeft);
            spinnerLeft.setOnItemSelectedListener(this);
            spinnerLeft.setBackgroundResource(R.drawable.turn);
            spinnerLeft.setPopupBackgroundResource(R.drawable.turn);

            spinnerRight.setAdapter(spinnerAdapterRight);
            spinnerRight.setOnItemSelectedListener(this);
            spinnerRight.setBackgroundResource(R.drawable.turn);
            spinnerRight.setPopupBackgroundResource(R.drawable.turn);

            playerYou = new Player("You", true, true, 0);
            spinnerRight.setSelection(playerSelected);

            gameStatus = "NO";
        }else{
            gameLevel = savedInstanceState.getInt("gameLevel");

            if(savedInstanceState.getString("spinnerLeftSelectedItem").equals("You")){
                spinnerLeft.setAdapter(spinnerAdapterLeft);
                spinnerLeft.setOnItemSelectedListener(this);
                spinnerLeft.setBackgroundResource(R.drawable.turn);
                spinnerLeft.setPopupBackgroundResource(R.drawable.turn);

                spinnerRight.setAdapter(spinnerAdapterRight);
                spinnerRight.setOnItemSelectedListener(this);
                spinnerRight.setBackgroundResource(R.drawable.turn);
                spinnerRight.setPopupBackgroundResource(R.drawable.turn);
            }else{
                spinnerLeft.setAdapter(spinnerAdapterRight);
                spinnerLeft.setOnItemSelectedListener(this);
                spinnerLeft.setBackgroundResource(R.drawable.turn);
                spinnerLeft.setPopupBackgroundResource(R.drawable.turn);

                spinnerRight.setAdapter(spinnerAdapterLeft);
                spinnerRight.setOnItemSelectedListener(this);
                spinnerRight.setBackgroundResource(R.drawable.turn);
                spinnerRight.setPopupBackgroundResource(R.drawable.turn);
            }

           spinnerLeft.setSelection(savedInstanceState.getInt("spinnerLeftSelected"));
           spinnerRight.setSelection(savedInstanceState.getInt("spinnerRightSelected"));

            String playCharacter;
            for(int i = 0; i < 9; i++){
                playCharacter = (String) savedInstanceState.getCharSequence(String.valueOf(i));

                ((TextView) gameBoard.getChildAt(i)).setText(playCharacter);

                if(playCharacter.equals("X")){
                    (gameBoardImageGrid.getChildAt(i)).setBackground(imageX);
                }else if(playCharacter.equals("O")){
                    (gameBoardImageGrid.getChildAt(i)).setBackground(imageO);
                }
            }

            turnTextView.setText(savedInstanceState.getCharSequence("turnTextView"));

            playerYou = new Player(
                    savedInstanceState.getString("playerYouName"),
                    savedInstanceState.getBoolean("playerYouIsPlayerX"),
                    savedInstanceState.getBoolean("playerYouIsMyTurn"),
                    savedInstanceState.getInt("playerYouScore"));

            playerOpponent = new Player(
                    savedInstanceState.getString("playerOpponentName"),
                    savedInstanceState.getBoolean("playerOpponentIsPlayerX"),
                    savedInstanceState.getBoolean("playerOpponentIsMyTurn"),
                    savedInstanceState.getInt("playerOpponentScore"));

            Player playerX = playerYou.isPlayerX() ? playerYou : playerOpponent;
            Player playerO = !playerYou.isPlayerX() ? playerYou : playerOpponent;

            playerXScoreTextView.setText(playerX.getScore() + "");
            playerOScoreTextView.setText(playerO.getScore() + "");

            gameStatus = savedInstanceState.getString("gameStatus");

            GameFunctions.checkWin(gameBoard, imageGameBoard);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        for(int i = 0; i < 9; i++){
           savedInstanceState.putCharSequence(String.valueOf(i), ((TextView)gameBoard.getChildAt(i)).getText());
        }

        savedInstanceState.putString("playerYouName", playerYou.getName());
        savedInstanceState.putBoolean("playerYouIsPlayerX", playerYou.isPlayerX());
        savedInstanceState.putBoolean("playerYouIsMyTurn", playerYou.isMyTurn());
        savedInstanceState.putInt("playerYouScore", playerYou.getScore());

        savedInstanceState.putString("playerOpponentName", playerOpponent.getName());
        savedInstanceState.putBoolean("playerOpponentIsPlayerX", playerOpponent.isPlayerX());
        savedInstanceState.putBoolean("playerOpponentIsMyTurn", playerOpponent.isMyTurn());
        savedInstanceState.putInt("playerOpponentScore", playerOpponent.getScore());

        savedInstanceState.putString("gameStatus", gameStatus);

        savedInstanceState.putInt("spinnerLeftSelected", spinnerLeft.getSelectedItemPosition());
        savedInstanceState.putString("spinnerLeftSelectedItem", spinnerLeft.getSelectedItem().toString());
        savedInstanceState.putInt("spinnerRightSelected", spinnerRight.getSelectedItemPosition());
        savedInstanceState.putInt("gameLevelSpinnerSelected", gameLevelSpinner.getSelectedItemPosition());
        savedInstanceState.putInt("gameLevel", gameLevel);

        savedInstanceState.putCharSequence("turnTextView", turnTextView.getText());
    }

        @Override
        public void onPause(){
            super.onPause();
            if(pw != null){
                pw.dismiss();
            }
        }

    @Override
    public void onStop(){
        super.onStop();
        if(pw != null){
            pw.dismiss();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(pw != null){
            pw.dismiss();
        }
    }

    View.OnClickListener ExitImageButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finishAffinity();
        }
    };

    View.OnClickListener MenuImageButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initiatePopupWindow();
        }
    };

    @Override
    public void onClick(View view){

        textView = (TextView) view;

        String textViewId = getResources().getResourceEntryName(textView.getId());
        textViewId = textViewId.replaceAll("[^0-9]","");
        int imageViewId = Integer.valueOf(textViewId);

        Player playerX = playerYou.isPlayerX() ? playerYou : playerOpponent;
        Player playerO = !playerYou.isPlayerX() ? playerYou : playerOpponent;

        if(playerX != null && playerO != null && gameStatus.equals("NO") && textView.getText().equals(" ")){
            if(playerX.isMyTurn()){
                (gameBoardImageGrid.getChildAt(imageViewId)).setBackground(imageX);

                textView.setText("X");
                turnTextView.setText("X's turn");
                playerX.setIsMyTurn(false);
                playerO.setIsMyTurn(true);
            }else{
                (gameBoardImageGrid.getChildAt(imageViewId)).setBackground(imageO);

                textView.setText("O");
                turnTextView.setText("O's turn");
                playerX.setIsMyTurn(true);
                playerO.setIsMyTurn(false);

            }

          if(gameLevel != 3) {
              gameStatus = GameFunctions.checkWin(gameBoard, imageGameBoard);

              boolean isPlayerX = playerX.isMyTurn() ? true : false;

              if (playerX.isMyTurn()) {
                  playerX.setIsMyTurn(false);
                  playerO.setIsMyTurn(true);
              } else {
                  playerX.setIsMyTurn(true);
                  playerO.setIsMyTurn(false);
              }

              if (gameStatus.equals("NO")) {
                  GameFunctions.playNextMove(gameBoard, gameBoardImageGrid, imageX, imageO, isPlayerX, turnTextView, gameLevel);
              }
          }

            gameStatus = GameFunctions.checkWin(gameBoard, imageGameBoard);
            switch (gameStatus){
                case "DRAW":
                    turnTextView.setText("It's a Draw");
                    break;
                case "X":
                    turnTextView.setText("X wins");
                    playerX.addScore();
                    playerXScoreTextView.setText(playerX.getScore() + "");
                    break;
                case "O":
                    turnTextView.setText("O wins");
                    playerO.addScore();
                    playerOScoreTextView.setText(playerO.getScore() + "");
                    break;
                default: break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = (String) parent.getItemAtPosition(position);
        if(parent.getId() == R.id.spinnerLeft || parent.getId() == R.id.spinnerRight) {
            if (!selectedItem.equals("You")) {
                if (playerOpponent == null) {
                    playerOpponent = new Player(selectedItem, !playerYou.isPlayerX(), !playerYou.isMyTurn(), 0);
                    resetButton.performClick();
                    startGameButton.performClick();
                } else if (!selectedItem.equals(playerOpponent.getName())) {
                    playerOpponent.setName(selectedItem);
                    playerOpponent.setScore(0);
                    playerYou.setScore(0);
                    resetButton.performClick();
                    startGameButton.performClick();
                }
                playerSelected = position;
                dbHelper.updateTableValue(TABLE_NAME, PLAYER_SELECTED, 0, playerSelected);
            }
        }else if(parent.getId() == R.id.gameLevelSpinner){

            if(playerYou != null && playerOpponent != null && gameLevel != position) {
                gameLevel = position;
                resetButton.performClick();
                startGameButton.performClick();
            }

            dbHelper.updateTableValue(TABLE_NAME, GAME_LEVEL, 0, gameLevel);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    View.OnClickListener StartGameButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           // Log.i("StartGameListener", "StartGameListener");
            resetGame();

            if(playerOpponent.isPlayerX() && gameLevel != 3){
                Random r = new Random(System.currentTimeMillis());
                int random = r.nextInt(2);
                int random2 = r.nextInt(4);
                if(random == 1){
                    ((TextView)gameBoard.getChildAt(xStart[random2])).setText("X");
                    (gameBoardImageGrid.getChildAt(xStart[random2])).setBackground(imageX);
                }else if(random == 0){
                    ((TextView)gameBoard.getChildAt(4)).setText("X");
                    (gameBoardImageGrid.getChildAt(4)).setBackground(imageX);
                }
                playerOpponent.setIsMyTurn(false);
                playerYou.setIsMyTurn(true);
                gameStatus = "NO";
                turnTextView.setText("O's turn");
            }else{
                gameStatus = "NO";
                turnTextView.setText("X's turn");
            }
        }
    };

    View.OnClickListener SwitchButtonHandler = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            resetGame();
            int selectedItem;

            if(spinnerLeft.getCount() > 1){
                selectedItem = spinnerLeft.getSelectedItemPosition();
                spinnerLeft.setAdapter(spinnerAdapterLeft);
                spinnerRight.setAdapter(spinnerAdapterRight);
                spinnerRight.setSelection(selectedItem);
                playerXScoreTextView.setText(String.valueOf(playerYou.getScore()));
                playerOScoreTextView.setText(String.valueOf(playerOpponent.getScore()));
                playerYou.setIsPlayerX(true);
                playerYou.setIsMyTurn(true);
                playerOpponent.setIsPlayerX(false);
                playerOpponent.setIsMyTurn(false);
                startGameButton.performClick();
            }else{
                selectedItem = spinnerRight.getSelectedItemPosition();
                spinnerLeft.setAdapter(spinnerAdapterRight);
                spinnerLeft.setSelection(selectedItem);
                spinnerRight.setAdapter(spinnerAdapterLeft);
                playerXScoreTextView.setText(String.valueOf(playerOpponent.getScore()));
                playerOScoreTextView.setText(String.valueOf(playerYou.getScore()));
                playerYou.setIsPlayerX(false);
                playerOpponent.setIsPlayerX(true);
                startGameButton.performClick();
            }
        }
    };

    View.OnClickListener ResetButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playerYou.setScore(0);
            playerOpponent.setScore(0);
            playerXScoreTextView.setText("0");
            playerOScoreTextView.setText("0");
            resetGame();
            startGameButton.performClick();
        }
    };

    public void resetGame(){
        for(int i = 0; i < 9; i++){
            ((TextView) gameBoard.getChildAt(i)).setText(" ");
            (gameBoardImageGrid.getChildAt(i)).setBackground(null);
        }
        imageGameBoard.setImageBitmap(null);
        if(isTextPlayCharacter){
            ColorFunctions.setTextColors(gameBoard);
        }
    }

    private void initiatePopupWindow() {
        if(pw != null){
            pw.dismiss();
        }

        try {       LayoutInflater inflater = LayoutInflater.from(this);
                    View layout = inflater.inflate(R.layout.popup_layout, (ViewGroup) findViewById(R.id.popupElement));

                    pw = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);


                    pw.showAtLocation(layout, Gravity.CLIP_VERTICAL, 0, 0);
                    // pw.showAsDropDown(gameHeader);

                    pw.getContentView().setEnabled(true);
                    pw.getContentView().setOnClickListener(CancelPopupElementHandler);


                    popupOKButton = (Button) pw.getContentView().findViewById(R.id.popupOKButton);
                    popupOKButton.setOnClickListener(PopupOKButtonHandler);

                    popupRadioGroup = (RadioGroup) pw.getContentView().findViewById(R.id.popupRadioGroup);
                    ((RadioButton) popupRadioGroup.getChildAt(characterCode)).setChecked(true);
                    popupRadioGroup.setOnCheckedChangeListener(PopupRadioGroupOnCheckedChangeListener);

                    imageView0 = (ImageView) pw.getContentView().findViewById(R.id.imageView0);
                    imageView0.setOnClickListener(ImageView0OnClickHandler);

                    imageView1 = (ImageView) pw.getContentView().findViewById(R.id.imageView1);
                    imageView1.setOnClickListener(ImageView1OnClickHandler);
/*
                    imageView2 = (ImageView) pw.getContentView().findViewById(R.id.imageView2);
                    imageView2.setOnClickListener(ImageView2OnClickHandler);
*/
            TextView textView2 = (TextView) pw.getContentView().findViewById(R.id.textView2);
            textView2.setOnClickListener(ImageView2OnClickHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View.OnClickListener CancelPopupElementHandler = new View.OnClickListener() {
        public void onClick(View v) {
            if(pw != null){
                pw.dismiss();
            }
        }
    };

    View.OnClickListener PopupOKButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(pw != null){
                pw.dismiss();
            }
        }
    };


    RadioGroup.OnCheckedChangeListener PopupRadioGroupOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            View checkedRadioButton = group.findViewById(checkedId);
            characterCode = group.indexOfChild(checkedRadioButton);
            dbHelper.updateTableValue(TABLE_NAME, CHARACTER_CODE, 0, characterCode);

            switch(characterCode){
                case 0: xPicName = "gold_x"; oPicName = "gold_o"; break;
                case 1: xPicName = "red_x"; oPicName = "red_o"; break;
                case 2: xPicName = "transparent"; oPicName = "transparent"; break;
                default: break;
            }

            for(int i = 0; i < 9; i++){
                ((TextView) gameBoard.getChildAt(i)).setTextColor(Color.TRANSPARENT);
            }
                try {

                    imageX = Drawable.createFromStream(assets.open(xPicName + ".png"), xPicName);
                    imageO = Drawable.createFromStream(assets.open(oPicName + ".png"), oPicName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(xPicName.equals("transparent")) {
                isTextPlayCharacter = true;
                ColorFunctions.setTextColors(gameBoard);
            }else{
                isTextPlayCharacter = false;
            }
            changeBoardCharacters();
        }
    };


    View.OnClickListener ImageView0OnClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((RadioButton)popupRadioGroup.getChildAt(0)).setChecked(true);
        }
    };

    View.OnClickListener ImageView1OnClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((RadioButton)popupRadioGroup.getChildAt(1)).setChecked(true);
        }
    };

    View.OnClickListener ImageView2OnClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((RadioButton)popupRadioGroup.getChildAt(2)).setChecked(true);
        }
    };


    public void changeBoardCharacters(){
        String squareCharacter;
        for(int i = 0; i < 9; i++){
            squareCharacter = ((TextView) gameBoard.getChildAt(i)).getText().toString();
            switch(squareCharacter) {
                case "X": (gameBoardImageGrid.getChildAt(i)).setBackground(imageX); break;
                case "O": (gameBoardImageGrid.getChildAt(i)).setBackground(imageO); break;
                default: break;
            }
        }
    }


}



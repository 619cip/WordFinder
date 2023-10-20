import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class WordFindHandler {

    private char[][] board;
    private final Stack<Pair<Integer, Integer>> path = new Stack<>();
    private Set<String> wordSet;
    private ArrayList<String> wordList;
    private final WordDictionary wordDict = new WordDictionary();
    private int MAX_LENGTH = 10;
    private int MAX_SIZE = 500;

    public WordFindHandler() throws IOException {
        board = new char[][]{};
    }
    public WordFindHandler(String board) throws Exception {
        this.board = convertBoardToArr(board);
    }
    public WordFindHandler(String board, int MAX_LENGTH) throws Exception {
        this.board = convertBoardToArr(board);
        this.MAX_LENGTH = MAX_LENGTH;
    }

    public WordFindHandler(String board, int MAX_LENGTH, int MAX_SIZE) throws Exception {
        this.board = convertBoardToArr(board);
        this.MAX_LENGTH = MAX_LENGTH;
        this.MAX_SIZE = MAX_SIZE;
    }

    // Convert Board to Array:
    //             board = [ [ A , B , C , D ],
    //                       [ E , F , G , H ],
    // "ABCDEFGHIJKLMNOP"->  [ I , J , K , L ],
    //                       [ M , N , O , P ] ]
    private char[][] convertBoardToArr(String str) throws Exception {
        this.wordList = new ArrayList<>();
        this.wordSet = new HashSet<>();
        str = str.toLowerCase();
        char[][] boardArr = new char[4][4];

        if (!(str.length() == 16))
            throw new Exception("Invalid board.");

        for (int i = 0; i < 16; i++) {
            if (!Character.isAlphabetic(str.charAt(i)))
                throw new Exception("Invalid letter on board.");
        }

        for (int i = 0, r = 0, c = 0; i < 16; i++) {
            boardArr[r][c] = str.charAt(i);
            c++;
            if (c % 4 == 0) {
                c = 0;
                r++;
            }
        }
        return boardArr;
    }

    public void setBoard(String str) throws Exception {
        this.board = convertBoardToArr(str);
    }
    private String convertWord() {
        String word = "";
        for (Pair<Integer,Integer> t: this.path) {
            word = word.concat(String.valueOf(this.board[t.getKey()][t.getValue()]));
        }
        return word;
    }

    private String isWord() {
        String word = convertWord();
        if (this.wordDict.getWordMap().containsKey(word)) {
            return word;
        }
        return "";
    }

    private void dfs(int r, int c) {
        int p_len = path.size();

        // Base case
        if (r < 0 || c < 0 ||
            r > 3 || c > 3 ||
            path.contains(new Pair<>(r,c))||
            p_len > MAX_LENGTH ||
            wordSet.size() >= MAX_SIZE)
            return;

        // Add letter indices to our stack
        path.add(new Pair<>(r,c));

        dfs(r - 1, c);         // Search left
        dfs(r + 1, c);         // Search right

        dfs(r, c - 1);         // Search up
        dfs(r, c + 1);         // Search down

        dfs(r - 1, c + 1);  // Search left-down
        dfs(r + 1, c + 1);  // Search right-down

        dfs(r - 1, c - 1);  // Search left-up
        dfs(r + 1, c - 1);  // Search right-up

        // Remove letter when reached maximum combinations -> MAX_LENGTH
        path.pop();

        // Is the word valid? If so, add to set.
        if (path.size() >= 3) {
            String word = isWord();
            if (!word.isEmpty()) {
                wordSet.add(word);
            }
        }
    }
    // Search for words at every letter
    public void search() {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                dfs(r,c);
            }
        }

        this.wordList = new ArrayList<>(this.wordSet);
        this.wordList.sort((o1, o2) -> o2.length()-o1.length());
    }

    public ArrayList<String> getWords() {
        return this.wordList;
    }

    public void setMAX_LENGTH(int num) {
        this.MAX_LENGTH = num;
    }

    public void setMAX_SIZE(int num) {
        this.MAX_SIZE = num;
    }
}

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class CardGame extends World {
  ArrayList<Card> arrCard;
  ArrayList<Card> stack;
  int numCards;
  int score;
  Back putBack;
  int currentTick;
  int finalTick;

  // constructor for CardGame
  CardGame(ArrayList<Card> arrCard, ArrayList<Card> stack) {
    this.arrCard = arrCard;
    this.stack = stack;
    this.numCards = arrCard.size();
    this.score = this.numCards / 2;
    this.currentTick = 0;
    this.finalTick = 200;
    this.putBack = new Back();
  }

  // constructor for CardGame
  CardGame() {
    this.arrCard = initialScene();
    this.stack = new ArrayList<Card>();
    this.numCards = arrCard.size();
    this.score = this.numCards / 2;
    this.currentTick = 0;
    this.finalTick = 200;
    this.putBack = new Back();
  }

  // make the scene of world with cards
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(700, 350);
    for (int index = 0; index < arrCard.size(); index++) {
      scene = arrCard.get(index).place(scene);
    }
    WorldImage scoreImage = new TextImage("Score: " + this.score, 20, Color.black);
    scene.placeImageXY(scoreImage, 560, 20);
    return scene;
  }

  public ArrayList<Card> initialScene() {
    // represents a list of suits
    ArrayList<String> los = new ArrayList<String>();
    los.add("♣");
    los.add("♦");
    los.add("♥");
    los.add("♠");

    // represents a list of cards
    ArrayList<Card> loc = new ArrayList<Card>();
    for (int i = 1; i < 14; i++) {
      for (int j = 0; j < 4; j++) {
        loc.add(new Card(los.get(j), i));
      }
    }

    // randomly arrange the position of each card
    Collections.shuffle(loc);

    for (int i = 0; i < 52; i++) {
      Card card = loc.get(i);
      int row;
      int column;

      if (i < 13) {
        row = i;
        column = 0;
      }
      else if (i < 26) {
        row = i - 13;
        column = 1;
      }
      else if (i < 39) {
        row = i - 26;
        column = 2;
      }
      else {
        row = i - 39;
        column = 3;
      }
      card.x = row * 50 + 50;
      card.y = column * 70 + 70;
    }
    for (int i = 0; i < 52; i++) {
      Card card = loc.get(i);
      // System.out.println(card.suit);
      // System.out.println(card.rank);
    }
    return loc;
  }

  // click the mouse to select the card and make card face up
  public void onMouseClicked(Posn pos) {
    Posn clickPosn = this.clickPosn(pos);
    if (this.stack.size() == 0) {
      for (Card card: arrCard) {
        if (clickPosn.x == card.x && clickPosn.y == card.y && !card.isFacingUp) {
          card.toFlip();
          stack.add(card);
          this.currentTick ++;
        }
      }
    }
    else if (this.stack.size() == 1) {
      for (Card card: arrCard) {
        if (clickPosn.x == card.x && clickPosn.y == card.y && !card.isFacingUp) {
          card.toFlip();
          stack.add(card);
          this.currentTick ++;
        }
      }
    }
  }

  // check if the mouse click on the card
  Posn clickPosn(Posn pos) {
    for (Card card: arrCard) {
      if (((pos.x - card.x >= 0 && pos.x - card.x <= 20)
          || (pos.x - card.x <= 0 && pos.x - card.x >= -20))
          && ((pos.y - card.y >= 0 && pos.y - card.y <= 30)
              || (pos.y - card.y <= 0 && pos.y - card.y >= -30))) {
        return new Posn(card.x, card.y);
      }
    }
    return new Posn(0, 0);
  }

  // for each tick, the scene will change
  public void onTick() {
    Utils util = new Utils();
    Random rand = new Random();


    this.numCards = this.arrCard.size();
    this.score = this.numCards / 2;
    this.putBack = new Back();
    this.currentTick ++;
    /*
    if (this.stack.size() == 2) {
      for (int i = 0; i < this.stack.size(); i++) {
        this.stack.get(i).toFlip();
      }
    }
    else*/
    if (this.wasMatch()) {
      this.arrCard.remove(this.stack.get(0));
      this.arrCard.remove(this.stack.get(1));
      this.score --;
    }
    if (this.stack.size() == 2) {
      pop();
      pop();
    }
  }

  // represent the onKey method
  // press r to reset the game
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      this.reset();
    }
  }

  // show the end scene of the game
  public WorldScene makeEndScene() {
    WorldScene endScene = new WorldScene(700, 350);
    WorldImage win = new TextImage("WIN", 100, Color.black);
    WorldImage lose = new TextImage("LOSE", 100, Color.black);
    if (this.score == 0) {
      endScene.placeImageXY(win, 320, 175);
    }
    else if (this.currentTick == this.finalTick) {
      endScene.placeImageXY(lose, 320, 175);
    }
    return endScene;
  }

  // check if the game ends
  public WorldEnd worldEnd() {
    if (this.score == 0) {
      return new WorldEnd(true, this.makeEndScene());
    }
    else if (this.currentTick == this.finalTick) {
      return new WorldEnd(true, this.makeEndScene());
    }
    return new WorldEnd(false, this.makeScene());
  }

  //add the selected card in a new array list
  void press(Card card) {
    stack.add(card);
  }

  // make the card face up
  void add(int n) {
    Card card = arrCard.get(n);
    if (!card.isFacingUp) {
      card.toFlip();
      press(card);
    }
  }

  // remove the non-paired card
  void pop(boolean flip) {
    int s = stack.size();
    if (s > 0) {
      Card card = stack.get(s - 1);
      stack.remove(s - 1);
      card.isFacingUp = !card.isFacingUp;
    }
  }

  // pop a card from the stack
  void pop() {
    pop(false);
  }

  // undo select a card
  void undo() {
    pop(true);
  }

  // check if two selected cards have the same rank
  boolean wasMatch() {
    boolean ret = (stack.size() == 2
        && stack.get(0).getNum() == stack.get(1).getNum());
    
    
    return ret;
  }

  // make the limitation of the size of the stack
  void selected(int n) {
    if (0 <= n && n < numCards) {
      switch (stack.size()) {
        case 2:
          undo();
          undo();
          break;
        case 0:
          add(n);
          break;
        case 1:
          add(n);
          wasMatch();
          break;
        default:
          throw new RuntimeException("Error: Stack size is too big.");
      }
    }
  }

  // get the cards and only show the face-up card
  public ArrayList<ICards> getCards() {
    ArrayList<ICards> ic = new ArrayList<ICards>();
    for (Card card : arrCard) {
      if (card.isFacingUp) {
        ic.add(card);
      }
      else {
        ic.add(putBack);
      }
    }
    return ic;
  }

  // reset the game and clear the stack
  public void reset() {
    for (Card card : arrCard) {
      if (card.isFacingUp) {
        card.toFlip();
      }
    }

    // shuffle
    Collections.shuffle(arrCard);

    this.stack = new ArrayList<Card>();
    this.numCards = arrCard.size();
    this.score = this.numCards / 2;
    this.putBack = new Back();
  }
}

class Utils {
  // apply the method in the list from left to right
  <T, U> U foldl(ArrayList<T> arr, BiFunction<T, U, U> func, U base) {
    for (T t : arr) {
      base = func.apply(t, base);
    }
    return base;
  }

  // function object to test if the arraylist contains the element that passes pred
  <T> boolean ormap(ArrayList<T> arr, Predicate<T> pred) {
    for (T t : arr) {
      if (pred.test(t)) {
        return true;
      }
    }
    return false;
  }
}

class Contain implements Predicate<Card> {
  String suit;
  int rank;

  Contain(String suit, int rank) {
    this.suit = suit;
    this.rank = rank;
  }


  public boolean test(Card t) {
    return (this.suit.equals(t.suit)) && (this.rank == t.rank);
  }
}

interface ICards {

}

// represent a card
class Card implements ICards {
  String suit;
  int rank;
  int x;
  int y;
  boolean isFacingUp;
  WorldImage cardFace;

  // constructor for card
  Card(String suit, int rank) {
    this.suit = suit;
    this.rank = rank;
    this.x = 0;
    this.y = 0;
    this.isFacingUp = false;
    this.cardFace = new TextImage("", Color.BLACK);
  }

  // constructor for card
  Card(String suit, int rank, int x, int y) {
    this.suit = suit;
    this.rank = rank;
    this.x = x;
    this.y = y;
    this.isFacingUp = false;
    this.cardFace = new TextImage("", Color.BLACK);
  }

  // get the number on the card
  public int getNum() {
    return this.rank;
  }

  // flip the card to be visible
  public void toFlip() {
    this.isFacingUp = !this.isFacingUp;
    
  }

  // represent the string on the card
  public String toString() {
    if (this.rank == 1) {
      return "Ace";
    }
    else if (this.rank == 11) {
      return "Jack";
    }
    else if (this.rank == 12) {
      return "Queen";
    }
    else if (this.rank == 13) {
      return "King";
    }
    else {
      return String.valueOf(this.rank);
    }
  }

  // change the position in x-axis
  public void changePosX(int index) {
    this.x = 50 * index;
  }

  // draw a card
  public WorldImage draw() {
    return new RectangleImage(40, 60, OutlineMode.OUTLINE, Color.BLACK);
  }

  // place the card on the world scene
  public WorldScene place(WorldScene u) {
    u.placeImageXY(this.draw(), this.x, this.y);
    if (this.isFacingUp) {
      this.cardFace = new TextImage(this.suit + " " + this.toString(), 10, Color.BLACK);
      u.placeImageXY(this.cardFace, this.x, this.y);
    }
    return u;
  }
}

// represents the card put back
class Back implements ICards {

  // get the number on the card
  public int getNum() {
    throw new RuntimeException("The card is invisible.");
  }

  // determine if the card is facing up to be visible
  public boolean isFaceUp() {
    return false;
  }

  // represent the string on the card
  public String toString() {
    return "*****";
  }
}

// examples for the card game concentration 
class ExamplesConcentration {
  ExamplesConcentration() {}

  // examples of cards
  Card card1 = new Card("♣", 1, 50, 70);
  Card card2 = new Card("♣", 2, 300, 210);
  Card card3 = new Card("♣", 3, 150, 280);

  Card card4 = new Card("♦", 1, 400, 70);
  Card card5 = new Card("♦", 2, 200, 280);
  Card card6 = new Card("♦", 3, 550, 140);

  // example of lists of cards
  ArrayList<Card> arr1 = new ArrayList<Card>(Arrays.asList(card1, card2, card3));
  ArrayList<Card> arr2 = new ArrayList<Card>(Arrays.asList(card4, card5, card6));

  // examples of lists of stack
  ArrayList<Card> stack = new ArrayList<Card>(Arrays.asList(card3));
  ArrayList<Card> stack1 = new ArrayList<Card>(Arrays.asList(card1, card3));
  ArrayList<Card> stack2 = new ArrayList<Card>(Arrays.asList(card2, card5));
  ArrayList<Card> stack3 = new ArrayList<Card>(Arrays.asList(card3, card4));

  CardGame cg1 = new CardGame(new ArrayList<Card>(), stack1);
  CardGame cg2 = new CardGame(new ArrayList<Card>(), stack2);
  CardGame cg3 = new CardGame(new ArrayList<Card>(), stack3);


  //test the method initialScene
  void testInitialScene(Tester t) {
    CardGame cg = new CardGame();
    Utils u = new Utils();

    ArrayList<Card> init = cg.initialScene();
    t.checkExpect(init.size(), 52);
    t.checkExpect(u.ormap(init, new Contain("♣", 1)), true);
    t.checkExpect(u.ormap(init, new Contain("♣", 2)), true);
    t.checkExpect(u.ormap(init, new Contain("♣", 3)), true);
    t.checkExpect(u.ormap(init, new Contain("♣", 4)), true);
    t.checkExpect(u.ormap(init, new Contain("♣", 5)), true);
    t.checkExpect(u.ormap(init, new Contain("♣", 6)), true);
    t.checkExpect(u.ormap(init, new Contain("♣", 7)), true);
    t.checkExpect(u.ormap(init, new Contain("♣", 8)), true);
    t.checkExpect(u.ormap(init, new Contain("♣", 9)), true);
    t.checkExpect(u.ormap(init, new Contain("♣", 10)), true);
    t.checkExpect(u.ormap(init, new Contain("♣", 11)), true);
    t.checkExpect(u.ormap(init, new Contain("♣", 12)), true);
    t.checkExpect(u.ormap(init, new Contain("♣", 13)), true);
  }

  // test the method onMouseClicked
  void testOnMouseClicked(Tester t) {
    CardGame cg = new CardGame();
    cg.onMouseClicked(new Posn(0, 0));
    boolean test = false;
    cg.onMouseClicked(new Posn(100, 140));
    t.checkExpect(cg.stack.size(), 1);
    t.checkExpect(cg.currentTick, 1);
    for (int i = 0; i < cg.arrCard.size(); i ++) {
      test = cg.arrCard.get(i).isFacingUp || test;
    }
    t.checkExpect(test, true);
  }

  // test the method clickPosn
  void testClickPosn(Tester t) {
    CardGame cg = new CardGame();
    t.checkExpect(cg.clickPosn(new Posn(0, 0)), new Posn(0, 0));
    t.checkExpect(cg.clickPosn(new Posn(50, 70)), new Posn(50, 70));
    t.checkExpect(cg.clickPosn(new Posn(300, 210)), new Posn(300, 210));
    t.checkExpect(cg.clickPosn(new Posn(150, 280)), new Posn(150, 280));
  }

  // test the method onTick
  void testOnTick(Tester t) {
    CardGame cg = new CardGame();
    cg.currentTick += 2;
    t.checkExpect(cg.arrCard.size(), 52); // it also may be 50 here for some conditions
    t.checkExpect(cg.stack.size(), 0);
  }

  // test the method onKeyEvent
  void testOnKeyEvent(Tester t) {
    CardGame cg = new CardGame();
    cg.arrCard = cg.initialScene();
    cg.stack = new ArrayList<Card>(Arrays.asList(cg.arrCard.get(4), cg.arrCard.get(13)));
    cg.numCards = 40;
    cg.score = 20;
    cg.currentTick = 36;
    cg.finalTick = 200;
    cg.putBack = new Back();

    cg.onKeyEvent("r");
    t.checkExpect(cg.arrCard.size(), 52);
    t.checkExpect(cg.stack.size(), 0);
    t.checkExpect(cg.numCards, 52);
    t.checkExpect(cg.score, 26);
    t.checkExpect(cg.putBack, new Back());
  }

  // test the method makeEndScene
  void testMakeEndScene(Tester t) {
    CardGame cg = new CardGame();
    WorldScene end1 = new WorldScene(700, 350);
    WorldScene end2 = new WorldScene(700, 350);
    WorldImage win = new TextImage("WIN", 100, Color.black);
    WorldImage lose = new TextImage("LOSE", 100, Color.black);
    end1.placeImageXY(win, 320, 175);
    end2.placeImageXY(lose, 320, 175);
    cg.score = 0;
    t.checkExpect(cg.makeEndScene(), end1);
    cg.score = 4;
    cg.currentTick = 200;
    t.checkExpect(cg.makeEndScene(), end2);
  }

  // test the method worldEnd
  void testWorldEnds(Tester t) {
    CardGame cg = new CardGame();
    t.checkExpect(cg.worldEnds(), new WorldEnd(false, cg.makeScene()));
  }

  // test the method press
  void testPress(Tester t) {
    CardGame cg4 = new CardGame(arr1, stack);
    cg4.press(card1);
    t.checkExpect(cg4.stack.size(), 2);
  }

  // test the method add
  void testAdd(Tester t) {
    CardGame cg4 = new CardGame(arr1, stack);
    cg4.add(0);
    t.checkExpect(cg4.arrCard.get(0).isFacingUp, true);
  }

  // test the method wasMatched for Model
  void testWasMatched(Tester t) {
    t.checkExpect(cg1.wasMatch(), false);
    t.checkExpect(cg2.wasMatch(), true);
    t.checkExpect(cg3.wasMatch(), false);
  }

  // test the method reset
  void testReset(Tester t) {
    CardGame cg = new CardGame();
    cg.reset();
    ArrayList<Card> cards = cg.initialScene();
    t.checkExpect(cards.size(), 52);
  }

  // test the method getNum
  void testGetNum(Tester t) {
    t.checkExpect(card1.getNum(), 1);
    t.checkExpect(card2.getNum(), 2);
    t.checkExpect(card3.getNum(), 3);
    t.checkExpect(card4.getNum(), 1);
    t.checkExpect(card5.getNum(), 2);
    t.checkExpect(card6.getNum(), 3);
  }

  // test the method toFlip
  void testToFlip(Tester t) {
    card1.toFlip();
    card2.toFlip();
    card3.toFlip();
    t.checkExpect(card1.isFacingUp, true);
    t.checkExpect(card2.isFacingUp, true);
    t.checkExpect(card3.isFacingUp, true);
  }

  // test the method toString
  void testToString(Tester t) {
    t.checkExpect(card1.toString(), "Ace");
    t.checkExpect(card2.toString(), "2");
    t.checkExpect(card3.toString(), "3");
  }

  // test the method changePosX
  void testChangePosX(Tester t) {
    card1.changePosX(1);
    card1.changePosX(8);
    card1.changePosX(6);
    t.checkExpect(card1.x, 300);
    t.checkExpect(card2.x, 300);
    t.checkExpect(card3.x, 150);
  }



  // test the method BigBang
  void testBigBang(Tester t) {
    CardGame cg = new CardGame();
    cg.reset();
    cg.bigBang(800, 600, 0.1);
  }
}
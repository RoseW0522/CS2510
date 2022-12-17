import java.util.*;

import tester.Tester;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = new ArrayList<Character>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code
  PermutationCode() {
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given random
  PermutationCode(Random rand) {
    this.rand = rand;
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given code
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
  }

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {
    // make a copy of the alphabet list
    ArrayList<Character> copy = new ArrayList<Character>();
    for (Character c : this.alphabet) {
      copy.add(c);
    }

    for (int i = 0; i < this.alphabet.size(); i++) {
      int index = this.rand.nextInt(26 - i);
      this.code.add(copy.remove(index));
    }
    return this.code;
  }

  // produce an encoded String from the given String
  String encode(String source) {
    String codedString = "";
    for (int i = 0; i < source.length(); i++) {
      int index = this.alphabet.indexOf(source.charAt(i));
      codedString = codedString + this.code.get(index);
    }
    return codedString;
  }

  // produce a decoded String from the given String
  String decode(String code) {
    String decodedString = "";
    for (int i = 0; i < code.length(); i++) {
      int index = this.code.indexOf(code.charAt(i));
      decodedString = decodedString + this.alphabet.get(index);
    }
    return decodedString;
  }
}

class ExamplesPermutation {
  Random rand1 = new Random(1);

  PermutationCode p = new PermutationCode(this.rand1);

  ArrayList<Character> code1;

  void initCode1() {
    code1 = new ArrayList<Character>();
    this.code1.add('r');
    this.code1.add('n');
    this.code1.add('h');
    this.code1.add('o');
    this.code1.add('y');
    this.code1.add('q');
    this.code1.add('t');
    this.code1.add('u');
    this.code1.add('l');
    this.code1.add('v');
    this.code1.add('a');
    this.code1.add('x');
    this.code1.add('g');
    this.code1.add('i');
    this.code1.add('k');
    this.code1.add('c');
    this.code1.add('e');
    this.code1.add('j');
    this.code1.add('z');
    this.code1.add('s');
    this.code1.add('f');
    this.code1.add('m');
    this.code1.add('d');
    this.code1.add('w');
    this.code1.add('b');
    this.code1.add('p');
  }

  // test the encode method
  void testerEncode(Tester t) {
    initCode1();

    t.checkExpect(this.p.encode(""), "");
    t.checkExpect(this.p.encode("hello"), "uyxxk");
    t.checkExpect(this.p.encode("aaaa"), "rrrr");
  }

  // test the decode method
  void testerDecode(Tester t) {
    initCode1();

    t.checkExpect(this.p.decode(""), "");
    t.checkExpect(this.p.decode("uyxxk"), "hello");
    t.checkExpect(this.p.decode("rrrr"), "aaaa");
  }

  // test the initEncoder method
  void testerInitEncoder(Tester t) {
    initCode1();

    t.checkExpect(this.p.code, this.code1);
    t.checkExpect(this.p.code.size(), 26);
  }

}

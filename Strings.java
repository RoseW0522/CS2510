// CS 2510, Assignment 3

import tester.*;

// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  // represent a list of all the occurrences of the first given String
  ILoString findAndReplace(String other, String that);

  // determine if the list contains any String more than once
  boolean anyDupes();
  
  //determine if the other string contained in the list more than once
  boolean anyDupesHelper(String other);
  
  //produce a new list sorted in alphabetical order
  ILoString sort();
  
  //determine if the string come lexicographically before the given string
  ILoString sortHelper(String other);
  
  //determine if the list is sorted in alphabetical order
  boolean isSorted();
  
  // determine if the string comes before the other string
  boolean isSortedHelper(String other);
  
  // use two lists to make a new list and each in odd and even position
  ILoString interleave(ILoString other);

  // combine two lists  by odd and even position
  ILoString interleaveHelper(ILoString other);
  
  //sort two lists by merge sort
  ILoString merge(ILoString other);
  
  // reverse the order of the list and combine all Strings in this list into one
  String reverseConcat();
  
  // reverse the order of the list by the accumulation of strings
  ILoString reverseConcatHelper(ILoString other);
  
  // determine if this list contains pairs of identical strings
  boolean isDoubledList();
  
  // determine if the first string of the list is the same as the other string
  boolean isDoubledListHelper(String other);
  
  // determine whether this list contains the same words reading the list in either order
  boolean isPalindromeList();
}


// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString(){}

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  // represent a list of all the occurrences of the first given String
  // replaced by the second given String
  public ILoString findAndReplace(String other, String that) {
    return this;
  }

  // determine if the list contains any String more than once
  public boolean anyDupes() {
    return false;
  }
  
  // determine if the other string contained in the list more than once
  public boolean anyDupesHelper(String other) {
    return false;
  }
  
  //produce a new list sorted in alphabetical order
  public ILoString sort() {
    return this;
  }
  
  //determine if the string come lexicographically before the given string
  public ILoString sortHelper(String other) {
    return new ConsLoString(other, this);
  }
  
  //determine if the list is sorted in alphabetical order
  public boolean isSorted() {
    return true;
  }
  
  // determine if the string comes before the other string
  public boolean isSortedHelper(String other) {
    return true;
  }
  
  // use two lists to make a new list and each in odd and even position
  public ILoString interleave(ILoString other) {
    return other;
  }

  // combine two lists  by odd and even position
  public ILoString interleaveHelper(ILoString other) {
    return other;
  }
  
  //sort two lists by merge sort
  public ILoString merge(ILoString other) {
    return other.sort();
  }
  
  //reverse the order of the list and combine all Strings in this list into one
  public String reverseConcat() {
    return reverseConcatHelper(new MtLoString()).combine();
  }

  // reverse the order of the list by the accumulation of strings
  public ILoString reverseConcatHelper(ILoString other) {
    return other;
  }
  
  //determine if this list contains pairs of identical strings
  public boolean isDoubledList() {
    return true;
  }

  // determine if the first string of the list is the same as the other string
  public boolean isDoubledListHelper(String other) {
    return false;
  }

  // determine whether this list contains the same words reading the list in either order
  public boolean isPalindromeList() {
    return true;
  }
  
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;  
  }

  /*
     TEMPLATE
     FIELDS:
     ... this.first ...         -- String
     ... this.rest ...          -- ILoString

     METHODS
     ... this.combine() ...                      -- String
     ... this.findAndReplace() ...               -- String
     ... this.anyDupes() ...                     -- boolean
     ... this.sort() ...                         -- ILoString
     ... this.sortHelper(String) ...             -- ILoString
     ... this.isSorted() ...                     -- boolean
     ... this.isSortedHelper(String) ...         -- boolean
     ... this.interleave(ILoString) ...          -- ILoString
     ... this.interleaveHelper(ILoString) ...    -- ILoString
     ... this.merge(ILoString) ...               -- ILoString
     ... this.reverseConcat() ...                -- String
     ... this.reverseConcatHelper(ILoString) ... -- ILoString
     ... this.isDoubledList() ...                -- boolean
     ... this.isDoubledListHelper(String) ...    -- boolean
     ... this.isPalindromeList() ...             -- boolean

     METHODS FOR FIELDS
     ... this.first.concat(String) ...                -- String
     ... this.first.compareTo(String) ...             -- int
     ... this.rest.combine() ...                      -- String
     ... this.rest.findAndReplaceHelper() ...         -- String
     ... this.rest.anyDupes() ...                     -- boolean
     ... this.first.equals(other) ...                 -- boolean
     ... this.rest.anyDupesHelper(other) ...          -- boolean
     ... rhis.rest.sort() ...                         -- ILoString
     ... this.rest.sortHelper(String)...              -- ILoString 
     ... this.rest.isSorted()...                      -- boolean 
     ... this.rest.isSortedHelper(String)...          -- boolean
     ... this.rest.interleave(ILoString)...           -- ILoString
     ... this.rest.interleaveHelper(ILostring)...     -- ILoString
     ... this.rest.merge(ILoString) ...               -- ILoString
     ... this.rest.reverseConcat() ...                -- String
     ... this.rest.reverseConcatHelper(ILoString) ... -- ILoString
     ... this.rest.isDoubledList() ...                -- boolean
     ... this.rest.isDoubledHelper(String)...         -- boolean
     ... this.rest.isPalindromwList() ...             -- boolean
   */

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  // takes in two Strings as arguments. It produces a list 
  // all occurrences of the first given String
  // replaced by the second given String
  public ILoString findAndReplace(String other, String that) {
    if (this.first.equals(other)) {
      return new ConsLoString(that, this.rest.findAndReplace(other, that));
    }
    else {
      return new ConsLoString(this.first, this.rest.findAndReplace(other, that));
    }
  }

  // determine if the list contains any String more than once
  public boolean anyDupes() {
    return this.rest.anyDupesHelper(this.first) || this.rest.anyDupes();
  }

  // determine if the other string contained in the list more than once
  public boolean anyDupesHelper(String other) {
    return this.first.equals(other) || this.rest.anyDupesHelper(other);
  }
  
  // produce a new list sorted in alphabetical order
  public ILoString sort() {
    return this.rest.sort().sortHelper(this.first);
  }
  
  // determine if the string come lexicographically before the given string
  public ILoString sortHelper(String other) {
    if (this.first.toLowerCase().compareTo(other.toLowerCase()) < 0
        || this.first.toLowerCase().compareTo(other.toLowerCase()) == 0) {
      return new ConsLoString(this.first, this.rest.sortHelper(other));
    }
    else {
      return new ConsLoString(other, this);
    }
  }
  
  // determine if the list is sorted in alphabetical order
  public boolean isSorted() {
    return this.rest.isSorted() && this.rest.isSortedHelper(this.first);
  }
  
  // determine if the string comes before the other string
  public boolean isSortedHelper(String other) {
    return other.toLowerCase().compareTo(this.first.toLowerCase()) <= 0;
  }
  
  // use two lists to make a new list and each in odd and even position
  public ILoString interleave(ILoString other) {
    return new ConsLoString(this.first, other.interleaveHelper(this.rest));
  }
  
  // combine two lists by odd and even position
  public ILoString interleaveHelper(ILoString other) {
    return new ConsLoString(this.first, other.interleave(this.rest));
  }
  
  // sort two lists by merge sort
  public ILoString merge(ILoString other) {
    return this.sort().interleave(other.sort()).sort();
  }
  
  // reverse the order of the list and combine all Strings in this list into one
  public String reverseConcat() {
    return this.reverseConcatHelper(new MtLoString()).combine();
  }

  // reverse the order of the list by the accumulation of strings
  public ILoString reverseConcatHelper(ILoString other) {
    return this.rest.reverseConcatHelper(new ConsLoString(this.first, other));
  }
  
  //determine if this list contains pairs of identical strings
  public boolean isDoubledList() {
    return this.rest.isDoubledListHelper(this.first);
  }

  // determine if the first string of the list is the same as the other string
  public boolean isDoubledListHelper(String other) {
    if (this.first == other) {
      return this.rest.isDoubledList();
    }
    else {
      return false;
    }
  }

  // determine whether this list contains the same words reading the list in either order
  public boolean isPalindromeList() {
    return this.interleave(this.reverseConcatHelper(new MtLoString())).isDoubledList();
  }
  
}

// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mary = new ConsLoString("Mary ",
      new ConsLoString("had ",
          new ConsLoString("a ",
              new ConsLoString("little ",
                  new ConsLoString("lamb.", new MtLoString())))));
  
  ILoString charlie = new ConsLoString("Charlie ",
      new ConsLoString("looks ",
          new ConsLoString("like ",
              new ConsLoString("a ",
                  new ConsLoString("little ",
                      new ConsLoString("bear.", new MtLoString()))))));
  
  ILoString unsorted = new ConsLoString("Lucia ",
      new ConsLoString("has ",
          new ConsLoString("many ",
              new ConsLoString("JellyCat ",
                  new ConsLoString("toys.", new MtLoString())))));
  
  ILoString doubledList1 = new ConsLoString("a",
      new ConsLoString("a",
          new ConsLoString("b",
              new ConsLoString("c",
                  new ConsLoString("d", new MtLoString())))));
  
  ILoString doubledList2 = new ConsLoString("d",
      new ConsLoString("e",
          new ConsLoString("e",
              new ConsLoString("f",
                  new ConsLoString("g", new MtLoString())))));
  
  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return 
        t.checkExpect(this.mary.combine(), "Mary had a little lamb.")
        && t.checkExpect(this.charlie.combine(), "Charlie looks like a little bear.");
  }
  
  // test the method findAndReplace for the lists of Strings
  boolean testFindAndReplace(Tester t) {
    return t.checkExpect(this.mary.findAndReplace("Mary ", "Lucia "), new ConsLoString("Lucia ",
              new ConsLoString("had ", new ConsLoString("a ", 
                  new ConsLoString("little ", new ConsLoString("lamb.",
                      new MtLoString()))))))
           && t.checkExpect(this.charlie.findAndReplace("bear.", "lamb."),
               new ConsLoString("Charlie ", new ConsLoString("looks ",
                   new ConsLoString("like ", new ConsLoString("a ",
                       new ConsLoString("little ", new ConsLoString("lamb.",
                           new MtLoString())))))))
           && t.checkExpect(this.doubledList2.findAndReplace("e", "a"),
               new ConsLoString("d", new ConsLoString("a",
                   new ConsLoString("a", new ConsLoString("f",
                       new ConsLoString("g", new MtLoString()))))));
  }
  
  //test the method anyDupes for the lists of Strings
  boolean testAnyDupes(Tester t) {
    return t.checkExpect(this.mary.anyDupes(), false)
           && t.checkExpect(this.charlie.anyDupes(), false);
  }
  
  //test the method anyDupesHelper for the lists of Strings
  boolean testAnyDupesHelper(Tester t) {
    return t.checkExpect(this.mary.anyDupesHelper("large "), false)
           && t.checkExpect(this.charlie.anyDupesHelper("lamb "), false);
  }
  
  
  //test the method sort for the lists of Strings
  boolean testSort(Tester t) {
    return t.checkExpect(this.mary.sort(), new ConsLoString("a ",
                                              new ConsLoString("had ",
                                                  new ConsLoString("lamb.",
                                                      new ConsLoString("little ",
                                                          new ConsLoString("Mary ",
                                                              new MtLoString()))))))
           && t.checkExpect(this.charlie.sort(), new ConsLoString("a ",
                                                     new ConsLoString("bear.",
                                                         new ConsLoString("Charlie ",
                                                             new ConsLoString("like ",
                                                                 new ConsLoString("little ",
                                                                     new ConsLoString("looks ",
                                                                         new MtLoString())))))));
  }
  
  //test the method sortHelper for the lists of Strings
  boolean testSortHelper(Tester t) {
    return t.checkExpect(this.mary.sort().sortHelper("is"), new ConsLoString("a ",
                                              new ConsLoString("had ",
                                                  new ConsLoString("is",
                                                      new ConsLoString("lamb.",
                                                          new ConsLoString("little ",
                                                              new ConsLoString("Mary ",
                                                                  new MtLoString())))))))
           && t.checkExpect(this.charlie.sort().sortHelper("lovely"), new ConsLoString("a ",
                                                 new ConsLoString("bear.",
                                                     new ConsLoString("Charlie ",
                                                         new ConsLoString("like ",
                                                             new ConsLoString("little ",
                                                                 new ConsLoString("looks ",
                                                                     new ConsLoString("lovely",
                                                                         new MtLoString()))))))));
  }
  
  // test the method isSorted for the lists of Strings
  boolean testIsSorted(Tester t) {
    return t.checkExpect(this.charlie.isSorted(), false)
           && t.checkExpect(this.charlie.sort().isSorted(), true)
           && t.checkExpect(this.mary.isSorted(), false)
           && t.checkExpect(this.unsorted.sort().isSorted(), true);
  }
  
  // test the method isSortedHelper for the lists of Strings
  boolean testIsSortedHelper(Tester t) {
    return t.checkExpect(this.charlie.sort().isSortedHelper("Aaa"), false)
            && t.checkExpect(this.mary.sort().isSortedHelper("Xxxx"), false);
  }
  
  // test the method interleave for the lists of Strings
  boolean testInterleave(Tester t) {
    return t.checkExpect(this.mary.interleave(this.unsorted), new ConsLoString("Mary ",
            new ConsLoString("Lucia ", new ConsLoString("had ",
                new ConsLoString("has ", new ConsLoString("a ",
                    new ConsLoString("many ", new ConsLoString("little ",
                        new ConsLoString("JellyCat ", new ConsLoString("lamb.",
                            new ConsLoString("toys.", new MtLoString())))))))))))
           && t.checkExpect(this.charlie.interleave(new MtLoString()), new ConsLoString("Charlie ",
               new ConsLoString("looks ", new ConsLoString("like ",
                   new ConsLoString("a ", new ConsLoString("little ",
                       new ConsLoString("bear.", new MtLoString())))))));
  }
  
  // test the method interleaveHelper for the lists of Strings
  boolean testInterleaveHelper(Tester t) {
    return t.checkExpect(this.mary.interleaveHelper(this.unsorted), new ConsLoString("Mary ",
        new ConsLoString("Lucia ", new ConsLoString("had ",
            new ConsLoString("has ", new ConsLoString("a ",
                new ConsLoString("many ", new ConsLoString("little ",
                    new ConsLoString("JellyCat ", new ConsLoString("lamb.",
                        new ConsLoString("toys.", new MtLoString())))))))))))
        && t.checkExpect(this.charlie.interleaveHelper(new MtLoString()), 
            new ConsLoString("Charlie ", new ConsLoString("looks ",
                new ConsLoString("like ", new ConsLoString("a ",
                    new ConsLoString("little ", new ConsLoString("bear.",
                        new MtLoString())))))));
  }
 
  // test the method merge for the lists of Strings
  boolean testMerge(Tester t) {
    return t.checkExpect(this.mary.merge(new MtLoString()), new ConsLoString("a ",
            new ConsLoString("had ", new ConsLoString("lamb.",
                new ConsLoString("little ", new ConsLoString("Mary ", 
                    new MtLoString()))))))
           && t.checkExpect(new MtLoString().merge(this.charlie), new ConsLoString("a ",
               new ConsLoString("bear.", new ConsLoString("Charlie ",
                   new ConsLoString("like ", new ConsLoString("little ",
                       new ConsLoString("looks ", new MtLoString())))))))
           && t.checkExpect(this.mary.merge(this.unsorted), new ConsLoString("a ",
               new ConsLoString("had ", new ConsLoString("has ",
                   new ConsLoString("JellyCat ", new ConsLoString("lamb.",
                       new ConsLoString("little ", new ConsLoString("Lucia ",
                           new ConsLoString("many ", new ConsLoString("Mary ",
                               new ConsLoString("toys.", new MtLoString())))))))))));
  }

  //test the method reverseConcat for the lists of Strings
  boolean testReverseConcat(Tester t) {
    return t.checkExpect(this.mary.sort().reverseConcat(),
              "Mary little lamb.had a ")
           && t.checkExpect(this.charlie.sort().reverseConcat(),
               "looks little like Charlie bear.a ");
  }

  //test the method reverseConcatHelper for the lists of Strings
  boolean testReverseConcatHelper(Tester t) {
    return t.checkExpect(this.mary.sort().reverseConcatHelper(new MtLoString()), 
            new ConsLoString("Mary ", new ConsLoString("little ",
                new ConsLoString("lamb.", new ConsLoString("had ",
                    new ConsLoString("a ", new MtLoString()))))))
          && t.checkExpect(this.charlie.sort().reverseConcatHelper(new MtLoString()),
              new ConsLoString("looks ", new ConsLoString("little ",
                  new ConsLoString("like ", new ConsLoString("Charlie ",
                      new ConsLoString("bear.", new ConsLoString("a ",
                          new MtLoString())))))));
  }

  //test the method isDoubledList for the lists of Strings
  boolean testIsDoubledList(Tester t) {
    return t.checkExpect(this.doubledList1.isDoubledList(), false)
           && t.checkExpect(this.doubledList2.isDoubledList(), false)
           && t.checkExpect(new MtLoString().isDoubledList(), true);
  }

  //test the method isdoubledListHelper for the lists of Strings
  boolean testIsDoubledListhelper(Tester t) {
    return t.checkExpect(this.unsorted.isDoubledListHelper("little "), false)
           && t.checkExpect(this.charlie.isDoubledListHelper("Mary "), false)
           && t.checkExpect(new MtLoString().isDoubledListHelper("Lucia "), false);
  }

  //test the method isPalindromeList for the lists of Strings
  boolean testIsPalindromeList(Tester t) {
    return t.checkExpect(this.charlie.isPalindromeList(), false)
           && t.checkExpect(this.unsorted.isPalindromeList(), false)
           && t.checkExpect(new MtLoString().isPalindromeList(), true);
  }
 
 
}
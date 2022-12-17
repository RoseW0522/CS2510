import tester.Tester;

// represents a Person with a user name and a list of buddies
class Person {
  
  String username;
  ILoBuddy buddies;
  double dictionScore;
  double hearingScore;

  Person(String username) {
    this.username = username;
    this.buddies = new MTLoBuddy();
    this.dictionScore = 0.0;
    this.hearingScore = 0.0;
  }

  Person(String username, double dictionScore, double hearingScore) {
    this.username = username;
    this.buddies = new MTLoBuddy();
    this.dictionScore = dictionScore;
    this.hearingScore = hearingScore;
  }

  // EFFECT:
  // Change this person's buddy list so that it includes the given person
  void addBuddy(Person buddy) {
    this.buddies = new ConsLoBuddy(buddy, this.buddies);
  }

  // determine if this Person has that as a direct buddy
  boolean hasDirectBuddy(Person that) {
    return this.buddies.findPerson(that);
  }

  // returns the number of people that are direct buddies of both this and that person
  int countCommonBuddies(Person that) {
    return this.buddies.countComBuddies(that);
  }

  // will the given person be (directly or indirectly) invited to a party
  // organized by this person?
  boolean hasExtendedBuddy(Person that) {
    return this.hasDirectBuddy(that) || this.hasExtendedBuddyHelper(that, new MTLoBuddy());
  }
  

  // determine that the buddies to see if that person is a friend of a friend in recursion.
  // acc: the people has already visited so far
  public boolean hasExtendedBuddyHelper(Person that, ILoBuddy gotten) {
    return this.buddies.countDisBuddies(that, gotten);
  }

  // returns the number of people who will show up at the party
  // given by this person
  int partyCount() {
    return 1 + this.buddies.countBuddies(new ConsLoBuddy(this, new MTLoBuddy()));
  }

  // returns the maximum likelihood that
  // this person can correctly convey a message to that person
  double maxLikelihood(Person that) {
    return this.maxLikelihoodHelper1(that, new ConsLoBuddy(this, new MTLoBuddy()));
  }
  
  // returns the maximum likelihood that
  // this person can correctly convey a message to the given person
  // ACC: people we have already visited so far
  double maxLikelihoodHelper1(Person that, ILoBuddy given) {
    if (this.equals(that)) {
      return 1.0;
    }
    else if (this.hasDirectBuddy(that)) {
      return this.maxLikelihoodHelper3(that);
    }
    else if (this.hasExtendedBuddy(that)) {
      return this.buddies.maxLikelihoodHelper2(this, that, given);
    }
    else {
      return 0.0;
    }
  }
  
  // returns the score of passing on a message to that person
  public double maxLikelihoodHelper3(Person that) {
    return this.dictionScore * that.hearingScore;
  }
  
}

// represents a list of Person's buddies
interface ILoBuddy {

  // determine if this person in the list of buddy
  boolean findPerson(Person that);

  // count the common buddies of the two person
  int countComBuddies(Person that);

  // determine if the given person will be invited to a party organized by this person
  boolean countDisBuddies(Person that, ILoBuddy gotten);

  // count the Buddies that will be invited
  int countBuddies(ILoBuddy acc);

  // returns the max likelihood that a message could be conveyed to
  // that person through this non empty list of buddies
  double maxLikelihoodHelper2(Person start, Person that, ILoBuddy visited);
}

// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
  MTLoBuddy() {}

  // determine if this person in the list of buddy
  public boolean findPerson(Person that) {
    return false;
  }

  // How many common buddy between that person and this empty list?
  public int countComBuddies(Person that) {
    return 0;
  }

  // determine if the given person will be invited to a party organized by this person
  public boolean countDisBuddies(Person that, ILoBuddy gotten) {
    return false;
  }

  // count the buddies in the empty list
  public int countBuddies(ILoBuddy acc) {
    return 0;
  }

  // returns the max likelihood that a message could be conveyed to
  // that person through this non empty list of buddies
  public double maxLikelihoodHelper2(Person start, Person that, ILoBuddy visited) {
    return 0;
  }
}

// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

  Person first;
  ILoBuddy rest;

  ConsLoBuddy(Person first, ILoBuddy rest) {
    this.first = first;
    this.rest = rest;
  }

  // determine if this person in the list of buddy
  public boolean findPerson(Person that) {
    return this.first.username.equals(that.username)
           || this.rest.findPerson(that);
  }

  // count the common buddies of the two person
  public int countComBuddies(Person that) {
    if (that.hasDirectBuddy(this.first)) {
      return 1 + this.rest.countComBuddies(that);
    }
    else {
      return this.rest.countComBuddies(that);
    }
  }

  // determine if the given person will be invited to a party organized by this person
  public boolean countDisBuddies(Person that, ILoBuddy gotten) {
    if (!gotten.findPerson(this.first)) {
      return this.first.hasDirectBuddy(that)
          || this.first.hasExtendedBuddyHelper(that, new ConsLoBuddy(this.first, gotten))
          || this.rest.countDisBuddies(that, new ConsLoBuddy(this.first, gotten));
    }
    else {
      return this.rest.countDisBuddies(that, gotten);
    }
  }

  // count the Buddies that will be invited
  public int countBuddies(ILoBuddy acc) {
    if (acc.findPerson(this.first)) {
      return this.rest.countBuddies(acc);
    }
    else {
      acc = new ConsLoBuddy(this.first, acc);
      return 1 + this.first.buddies.countBuddies(acc)
             + this.rest.countBuddies(acc)
             - this.countComBuddies(this.first);
    }
  }

  // returns the max likelihood that a message could be conveyed to
  // that person through this non empty list of buddies
  public double maxLikelihoodHelper2(Person start, Person that, ILoBuddy visited) {
    if (visited.findPerson(this.first)) {
      return this.rest.maxLikelihoodHelper2(start, that, visited);
    }
    else {
      return Math.max(
          start.maxLikelihoodHelper3(this.first)
              * this.first.maxLikelihoodHelper1(that, new ConsLoBuddy(this.first, visited)),
          this.rest.maxLikelihoodHelper2(start, that, new ConsLoBuddy(this.first, visited)));
    }
  }
}

// runs tests for the buddies problem
class ExamplesBuddies {

  // Examples of Person
  Person ann = new Person("Ann", 0.9, 0.5);
  Person bob = new Person("Bob", 0.8, 0.7);
  Person cole = new Person("Cole", 0.9, 0.6);
  Person dan = new Person("Dan", 0.5, 0.7);
  Person ed = new Person("Ed", 0.6, 0.7);
  Person fay = new Person("Fay",0.9, 1.0);
  Person gabi = new Person("Gabi", 1.0, 0.7);
  Person hank = new Person("Hank", 0.8, 1.0);
  Person jan = new Person("Jan", 0.6, 0.7);
  Person kim = new Person("Kim", 0.8, 0.9);
  Person len = new Person("Len", 0.5, 0.7);

  // Examples of Buddy lists
  ILoBuddy mt = new MTLoBuddy();
  ILoBuddy bl1 = new ConsLoBuddy(this.cole, new ConsLoBuddy(this.bob,
      new MTLoBuddy()));

  // initializes each person's Buddy lists
  void initBuddies() {
    this.ann.buddies = new ConsLoBuddy(this.bob, new ConsLoBuddy(this.cole, this.mt));
    this.bob.buddies = new ConsLoBuddy(this.ann, new ConsLoBuddy(this.ed,
        new ConsLoBuddy(this.hank, this.mt)));
    this.cole.buddies = new ConsLoBuddy(this.dan, this.mt);
    this.dan.buddies = new ConsLoBuddy(this.cole, this.mt);
    this.ed.buddies = new ConsLoBuddy(this.fay, this.mt);
    this.fay.buddies = new ConsLoBuddy(this.ed, new ConsLoBuddy(this.gabi, this.mt));
    this.gabi.buddies = new ConsLoBuddy(this.ed, new ConsLoBuddy(this.fay, this.mt));
    this.jan.buddies = new ConsLoBuddy(this.kim, new ConsLoBuddy(this.len, this.mt));
    this.kim.buddies = new ConsLoBuddy(this.jan, new ConsLoBuddy(this.len, this.mt));
    this.len.buddies = new ConsLoBuddy(this.jan, new ConsLoBuddy(this.kim, this.mt));
  }

  // test the method addBuddy for Person
  void testAddBuddy(Tester t) {
    initBuddies();
    t.checkExpect(ann.hasDirectBuddy(this.ed), false);
    this.ann.addBuddy(this.ed);
    t.checkExpect(ann.hasDirectBuddy(this.ed), true);
  }

  // test the method hasDirectBuddy for Person
  void testHasDirectBuddy(Tester t) {
    initBuddies();
    t.checkExpect(this.cole.hasDirectBuddy(this.dan), true);
    t.checkExpect(this.ed.hasDirectBuddy(this.ed), false);
    t.checkExpect(this.fay.hasDirectBuddy(this.dan), false);
  }

  // test the method findPerson for ILoBuddy
  void testFindPerson(Tester t) {
    initBuddies();
    t.checkExpect(this.ann.buddies.findPerson(this.bob), true);
    t.checkExpect(this.ann.buddies.findPerson(this.ed), false);
    t.checkExpect(this.len.buddies.findPerson(this.bob), false);
  }

  // test the method hasExtendedBuddy for Person
  void testHasExtendedBuddy(Tester t) {
    initBuddies();
    t.checkExpect(this.ann.hasExtendedBuddy(bob), true);
    t.checkExpect(this.ann.hasExtendedBuddy(fay), true);
    t.checkExpect(this.ed.hasExtendedBuddy(gabi), true);
    t.checkExpect(this.len.hasExtendedBuddy(bob), false);
  }
  
  // test the method hasExtendedBuddyHelper for Person
  void testHasExtendedBuddyHelper(Tester t) {
    initBuddies();
    t.checkExpect(this.ann.hasExtendedBuddyHelper(bob, this.ann.buddies), false);
    t.checkExpect(this.ed.hasExtendedBuddyHelper(gabi, this.gabi.buddies), false);
    t.checkExpect(this.len.hasExtendedBuddyHelper(bob, this.bob.buddies), false);
  }

  // test the method countComBuddies for ILoBuddy
  void testCountComBuddies(Tester t) {
    initBuddies();
    t.checkExpect(this.bob.buddies.countComBuddies(this.jan), 0);
    t.checkExpect(this.ann.buddies.countComBuddies(this.bob), 0);
    t.checkExpect(this.gabi.buddies.countComBuddies(this.ed), 1);
  }

  // test the method countCommonBuddies for Person
  void testCountCommonBuddiesP(Tester t) {
    initBuddies();
    t.checkExpect(this.ann.countCommonBuddies(this.bob), 0);
    t.checkExpect(this.gabi.countCommonBuddies(this.ed), 1);
    t.checkExpect(this.bob.countCommonBuddies(this.jan), 0);
  }

  // test the method partyCount
  void testPartyCount(Tester t) {
    initBuddies();
    t.checkExpect(this.ann.partyCount(), 8);
    t.checkExpect(this.gabi.partyCount(), 3);
    t.checkExpect(this.hank.partyCount(), 1);
    t.checkExpect(this.jan.partyCount(), 3);
  }

  // test the method maxLikelihood for Person
  void testMaxLikelihood(Tester t) {
    initBuddies();
    t.checkInexact(this.ann.maxLikelihood(this.hank), 0.504, 0.001);
    t.checkInexact(this.gabi.maxLikelihood(this.ed), 0.7, 0.001);
    t.checkInexact(this.jan.maxLikelihood(this.kim), 0.54, 0.001);
  }
  
  // test the method maxLikelihoodHelper1 for Person
  void testMaxLikelihoodHelper1(Tester t) {
    initBuddies();
    t.checkInexact(this.ann.maxLikelihoodHelper1(this.hank, this.ann.buddies), 0.0, 0.001);
    t.checkInexact(this.gabi.maxLikelihoodHelper1(this.ed, this.gabi.buddies), 0.7, 0.001);
    t.checkInexact(this.jan.maxLikelihoodHelper1(this.kim, this.jan.buddies), 0.54, 0.001);
  }
  
  
  // test the method maxLikelihoodHelper2 for ILoBuddy
  void testMaxLikelihoodHelper2(Tester t) {
    initBuddies();
    t.checkInexact(this.ann.buddies.maxLikelihoodHelper2(this.bob, this.hank, this.ann.buddies),
        0.0, 0.001);
    t.checkInexact(this.gabi.buddies.maxLikelihoodHelper2(this.gabi, this.ed, this.gabi.buddies),
        0.0, 0.001);
    t.checkInexact(this.jan.buddies.maxLikelihoodHelper2(this.jan, this.kim, this.jan.buddies),
        0.0, 0.001);
  }
  
  // test the method maxLikelihoodHelper3 for Person
  void testMaxLikelihoodHelper3(Tester t) {
    initBuddies();
    t.checkInexact(this.bob.maxLikelihoodHelper3(this.hank), 0.8, 0.001);
    t.checkInexact(this.gabi.maxLikelihoodHelper3(this.ed), 0.7, 0.001);
    t.checkInexact(this.jan.maxLikelihoodHelper3(this.kim), 0.54, 0.001);
  }
  
  
}
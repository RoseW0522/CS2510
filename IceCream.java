interface IIceCream {}

//to represent an EmptyServing IceCream
class EmptyServing implements IIceCream {
  boolean cone;

  EmptyServing(boolean cone) {
    this.cone = cone;
  }
}

//to represent a Scooped IceCream
class Scooped implements IIceCream {
  IIceCream more;
  String flavor;

  Scooped(IIceCream more, String flavor) {
    this.more = more;
    this.flavor = flavor;
  }
}

//to represent examples and tests for IceCreams
class ExamplesIceCream {
  ExamplesIceCream() {}

  EmptyServing cup = new EmptyServing(false);
  EmptyServing cone = new EmptyServing(true);

  Scooped orderA = new Scooped(this.cup, "mint chip");
  Scooped orderB = new Scooped(this.orderA, "coffee");
  Scooped orderC = new Scooped(this.orderB, "black raspberry");
  Scooped order1 = new Scooped(this.orderC, "caramel swirl");

  Scooped orderD = new Scooped(this.cone, "chocolate");
  Scooped orderE = new Scooped(this.orderD, "vanilla");
  Scooped order2 = new Scooped(this.orderE, "strawberry");
}
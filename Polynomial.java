import tester.Tester;

class Monomial {
  int degree;
  int coefficient;
  
  Monomial(int degree, int coefficient) {
    if (degree >= 0) {
      this.degree = degree;
    }
    else {
      throw new IllegalArgumentException("Degrees of monomials cannot be negative");
    }
    this.coefficient = coefficient;
  }
  
  /* TEMPLATE:
   * 
   * Fields:
   * ... this.degree ...      -- int
   * ... this.coefficient ... -- int
   * 
   * Methods:
   * ... this.isZero() ...      -- boolean
   * ... this.sameMonomial(Monomial) .. -- boolean
   * ... this.sameDegree(Monomial) ...  -- boolean
   * 
   * Methods for fields:
   * 
   * 
   */
  
  // determine if the coefficient of the Monomial is zero
  public boolean isZero() {
    return this.coefficient == 0;
  }
  
  // determine if two Monomials are the same
  public boolean sameMonomial(Monomial other) {
    return this.degree == other.degree
           && this.coefficient == other.coefficient;
  }
  
  // determine if two Monomials have the same degree
  public boolean sameDegree(Monomial other) {
    return this.degree == other.degree;
  }
}

// represent a list of monomials
interface ILoMonomial {
  
  // determine if two ILoMonomials are the same
  boolean sameMonomials(ILoMonomial other);
  
  // determine if the ILoMonomial is empty
  boolean isEmpty();
  
  // remove the monomial with a zero coefficient in the list of monomials
  ILoMonomial remove();
  
  // find the monomial and remove from the list of monomials
  ILoMonomial findAndRemove(Monomial other);
  
  // determine if the list of monomials have any duplicate
  boolean anyDuplicate();
  
  // determine if any monomials have the same degree in the list of monomials
  boolean anyDuplicateHelper(Monomial other);
}

class MtLoMonomial implements ILoMonomial {
  
  /* TEMPLATE:
   * 
   * Fields:
   * 
   * Methods:
   * ... this.sameMonomials(ILoMonomial) ... -- boolean
   * ... this.isEmpty() ..                   -- boolean
   * ... this.remove() ...                   -- ILoMonomial
   * ... this.findAndRemove(Monomial) ...    -- boolean
   * ... this.anyDuplicates() ...            -- boolean
   * ... this.anyDuplicateHelper(Monomial) ...   -- boolean
   * 
   * Methods for fields:
   * 
   */
  
  //determine if two ILoMonomials are the same
  public boolean sameMonomials(ILoMonomial other) {
    return other.isEmpty();
  }

  // determine if the ILoMonomial is empty
  public boolean isEmpty() {
    return true;
  }

  // remove the monomial with a zero coefficient in the list of monomials
  public ILoMonomial remove() {
    return this;
  }

  // find the monomial and remove from the list of monomials
  public ILoMonomial findAndRemove(Monomial other) {
    return this;
  }

  // determine if the list of monomials have any duplicate
  public boolean anyDuplicate() {
    return false;
  }

  // determine if any monomials have the same degree in the list of monomials
  public boolean anyDuplicateHelper(Monomial other) {
    return false;
  }
}

class ConsLoMonomial implements ILoMonomial {
  Monomial first;
  ILoMonomial rest;
  
  ConsLoMonomial(Monomial first, ILoMonomial rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /* TEMPLATE:
   * 
   * Fields:
   * ... this.first ... -- Monomial
   * ... this.rest ...  -- ILoMonomial
   * 
   * Methods:
   * ... this.sameMonomials(ILoMonomial) ...  -- boolean
   * ... this.isEmpty() ..                    -- boolean
   * ... this.remove() ...                    -- ILoMonomial
   * ... this.findAndRemove(Monomial) ...     -- boolean
   * ... this.anyDuplicates() ...             -- boolean
   * ... this.anyDuplicateHelper(Monomial) ...    -- boolean
   * 
   * Methods for fields:
   * ... this.rest.sameMonomials(ILoMonomial) ... -- boolean
   * ... this.first.sameMonomial(ILoMonomial) ... -- boolean
   * ... this.rest.findAndRemove(Monomial) ...    -- boolean
   * 
   */
  
  //determine if two ILoMonomials are the same
  public boolean sameMonomials(ILoMonomial other) {
    return this.rest.sameMonomials(other.findAndRemove(this.first));
  }

  // determine if the ILoMonomial is empty
  public boolean isEmpty() {
    return false;
  }

  // remove the monomial with a zero coefficient in the list of monomials
  public ILoMonomial remove() {
    if (this.first.isZero()) {
      return this.rest.remove();
    }
    else {
      return new ConsLoMonomial(this.first, this.rest.remove());
    }
  }

  // find the monomial and remove from the list of monomials
  public ILoMonomial findAndRemove(Monomial other) {
    if (this.first.sameMonomial(other)) {
      return this.rest;
    }
    else {
      return new ConsLoMonomial(this.first, this.rest.findAndRemove(other));
    }
  }

  // determine if the list of monomials have any duplicate
  public boolean anyDuplicate() {
    return this.rest.anyDuplicateHelper(this.first)
           || this.rest.anyDuplicate();
  }

  // determine if any monomials have the same degree in the list of monomials
  public boolean anyDuplicateHelper(Monomial other) {
    return this.first.sameDegree(other) || this.rest.anyDuplicateHelper(other);
  }
}

class Polynomial {
  ILoMonomial monomials;
  
  Polynomial(ILoMonomial monomials) {
    if (monomials.anyDuplicate()) {
      throw new IllegalArgumentException(
          "Polymonials cannot have moomials withe the same degrees.");
    }
    else {
      this.monomials = monomials;
    }
  }
  
  /* TEMPLATE:
   * 
   * Fields:
   * ... this.monomials ... -- ILoMonomial
   * 
   * Methods:
   * ... this.samePolynomial() ... -- boolean
   * 
   * Methods for fields:
   * ... this.remove() ... -- ILoMonomial
   * ... this.sameMonomials() ... -- boolean
   * 
   */
  
  // determine if two polynomials are equal
  public boolean samePolynomial(Polynomial other) {
    return this.monomials.remove().sameMonomials(other.monomials.remove())
           && other.monomials.remove().sameMonomials(this.monomials.remove()); 
  } 
}

class ExamplesPolynomial {
  
  Monomial m1 = new Monomial(1, 1);
  Monomial m2 = new Monomial(2, 2);
  Monomial m3 = new Monomial(3, 3);
  Monomial m4 = new Monomial(0, 5);
  
  ILoMonomial mtLoM = new MtLoMonomial();
  ILoMonomial iLoM1 = new ConsLoMonomial(new Monomial(0, 0),
                         new ConsLoMonomial(new Monomial(1, 1),
                             new ConsLoMonomial(new Monomial(2, 2),
                                 new MtLoMonomial())));
  
  ILoMonomial iLoM2 = new ConsLoMonomial(new Monomial(1, 1),
                         new ConsLoMonomial(new Monomial(2, 2),
                             new ConsLoMonomial(new Monomial(3, 3),
                                 new MtLoMonomial())));
  
  ILoMonomial iLoM3 = new ConsLoMonomial(new Monomial(4, 0),
                         new ConsLoMonomial(new Monomial(2, 3),
                             new ConsLoMonomial(new Monomial(8, 3),
                                 new MtLoMonomial())));
  
  ILoMonomial manyZero = new ConsLoMonomial(new Monomial(4, 0),
                            new ConsLoMonomial(new Monomial(2, 3),
                                new ConsLoMonomial(new Monomial(16, 0),
                                    new ConsLoMonomial(new Monomial(28, 0),
                                        new MtLoMonomial()))));
  
  ILoMonomial illegalMonomials1 = new ConsLoMonomial(new Monomial(1, 1),
                                     new ConsLoMonomial(new Monomial(2, 2),
                                         new ConsLoMonomial(new Monomial(3, 2),
                                             new MtLoMonomial())));
  
  ILoMonomial illegalMonomials2 = new ConsLoMonomial(new Monomial(1, 2),
                                     new ConsLoMonomial(new Monomial(2, 3),
                                         new ConsLoMonomial(new Monomial(3, 3),
                                             new MtLoMonomial())));
  
  Polynomial p1 = new Polynomial(this.iLoM1);
  Polynomial p2 = new Polynomial(this.iLoM2);
  Polynomial p3 = new Polynomial(this.iLoM3);
  Polynomial p4 = new Polynomial(this.mtLoM);
  
  // test for the method sameMonomial
  boolean testSameMonomial(Tester t) {
    return t.checkExpect(m1.sameMonomial(m2), false)
           && t.checkExpect(m3.sameMonomial(m4), false)
           && t.checkExpect(m2.sameMonomial(m3), false);
  }
  
  // test for the method isZeros
  boolean testIsZero(Tester t) {
    return t.checkExpect(m1.isZero(), false)
        && t.checkExpect(m3.isZero(), false)
        && t.checkExpect(m4.isZero(), false);
  }
  
  // test for the method sameDegree
  boolean testSameDegree(Tester t) {
    return t.checkExpect(m1.sameDegree(m2), false)
        && t.checkExpect(m3.sameDegree(m2), false)
        && t.checkExpect(m4.sameDegree(m1), false);
  }
  
  // test for the method sameMonomials
  boolean testSameMonomials(Tester t) {
    return t.checkExpect(iLoM1.sameMonomials(iLoM2), false)
           && t.checkExpect(iLoM2.sameMonomials(iLoM3), false)
           && t.checkExpect(iLoM3.sameMonomials(iLoM1), false);
  }

  // test for the method isEmpty
  boolean testIsEmpty(Tester t) {
    return t.checkExpect(manyZero.isEmpty(), false)
           && t.checkExpect(iLoM1.isEmpty(), false)
           && t.checkExpect(iLoM2.isEmpty(), false);
  }

  //test for the method remove
  boolean testRemove(Tester t) {
    return t.checkExpect(manyZero.remove(), 
              new ConsLoMonomial(new Monomial(2, 3),
                  new MtLoMonomial()))
           && t.checkExpect(iLoM1.remove(), 
               new ConsLoMonomial(new Monomial(1, 1),
                   new ConsLoMonomial(new Monomial(2, 2),
                       new MtLoMonomial())))
           && t.checkExpect(iLoM2.remove(), 
               new ConsLoMonomial(new Monomial(1, 1),
                   new ConsLoMonomial(new Monomial(2, 2),
                       new ConsLoMonomial(new Monomial(3, 3),
                           new MtLoMonomial()))));
  }
  
  //test for the method findAndRemove
  boolean testFindAndRemovw(Tester t) {
    return t.checkExpect(manyZero.remove().findAndRemove(m1),
           new ConsLoMonomial(new Monomial(2, 3),
               new MtLoMonomial()))
           && t.checkExpect(iLoM1.remove().findAndRemove(m2),
               new ConsLoMonomial(new Monomial(1, 1),
                   new MtLoMonomial()))
           && t.checkExpect(iLoM3.remove().findAndRemove(m4),
               new ConsLoMonomial(new Monomial(2, 3),
                   new ConsLoMonomial(new Monomial(8, 3),
                       new MtLoMonomial())));
  }
  
  //test for the method anyDuplicate
  boolean testAnyDuplicate(Tester t) {
    return t.checkExpect(this.illegalMonomials1.anyDuplicate(), false)
           && t.checkExpect(this.illegalMonomials2.anyDuplicate(), false)
           && t.checkExpect(this.iLoM1.anyDuplicate(), false)
           && t.checkExpect(this.iLoM2.anyDuplicate(), false);
  }
  
  //test for the method anyDupulicatehelper
  boolean testAnyDuplicateHelper(Tester t) {
    return t.checkExpect(this.illegalMonomials1.anyDuplicateHelper(this.m4), false)
           && t.checkExpect(this.illegalMonomials2.anyDuplicateHelper(this.m3), true)
           && t.checkExpect(this.iLoM1.anyDuplicateHelper(this.m2), true)
           && t.checkExpect(this.iLoM2.anyDuplicateHelper(this.m1), true);
  }
  
  //test for the method samePolynomial
  boolean testSamePolynomial(Tester t) {
    return t.checkExpect(this.p1.samePolynomial(p1), true)
           && t.checkExpect(this.p1.samePolynomial(p2), false)
           && t.checkExpect(this.p3.samePolynomial(p4), false)
           && t.checkExpect(this.p2.samePolynomial(p3), false);
  }
}










//to represent a post
class Post {
  String user;
  String text;
  int likes;
  int timeStamp;

  Post(String user, String text, int likes, int timeStamp) {
    this.user = user;
    this.text = text;
    this.likes = likes;
    this.timeStamp = timeStamp;
  }
}

//to represent examples and tests for posts
class ExamplesPost {
  ExamplesPost(){}

  Post personalNews = new Post("iheartfundies",
      "Some personal news: I will be taking fundies 2 this fall",
      200,
      1625699955);
  Post cupcakeAd = new Post("thequeenscups",
      "life is too short to not eat cupcakes",
      48,
      1631661555);
  Post dailyLife = new Post("iloveboston",
      "Today I made a try of cooking a lobster",
      14,
      1641341455);
}
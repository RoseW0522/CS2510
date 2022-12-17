import tester.Tester;

// a piece of media
interface IMedia {

  // is this media really old?
  boolean isReallyOld();

  // are captions available in this language?
  boolean isCaptionAvailable(String language);

  // a string showing the proper display of the media
  String format();
}

abstract class AMedia implements IMedia {
  String title;
  ILoString captionOptions;

  // constructor Media
  AMedia(String title, ILoString captionOptions) {
    this.title = title;
    this.captionOptions = captionOptions;
  }

  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.containsCaption(language);
  }
}

// represents a movie
class Movie extends AMedia {
  int year;

  Movie(String title, int year, ILoString captionOptions) {
    super(title, captionOptions);
    this.year = year;
  }

  public boolean isReallyOld() {
    return this.year < 1930;
  }

  public String format() {
    return this.title + " (" + this.year + ")";
  }
}

// represents a TV episode
class TVEpisode extends AMedia {
  String showName;
  int seasonNumber;
  int episodeOfSeason;

  TVEpisode(String title, String showName, int seasonNumber, int episodeOfSeason,
      ILoString captionOptions) {
    super(title, captionOptions);
    this.showName = showName;
    this.seasonNumber = seasonNumber;
    this.episodeOfSeason = episodeOfSeason;
  }

  public boolean isReallyOld() {
    return false;
  }

  public String format() {
    return this.showName + " " + this.seasonNumber + "."
           + this.episodeOfSeason + " - " + this.title;
  }
}

// represents a YouTube video
class YTVideo extends AMedia {
  String channelName;

  public YTVideo(String title, String channelName, ILoString captionOptions) {
    super(title, captionOptions);
    this.channelName = channelName;
  }

  public boolean isReallyOld() {
    return false;
  }

  public String format() {
    return this.title + " by " + this.channelName;
  }

}

// lists of strings
interface ILoString {
  boolean containsCaption(String language);
}

// an empty list of strings
class MtLoString implements ILoString {

  public boolean containsCaption(String language) {
    return false;
  }
}

// a non-empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  public boolean containsCaption(String language) {
    if (this.first.equals(language)) {
      return true;
    }
    else {
      return this.rest.containsCaption(language);
    }
  }
}

class ExamplesMedia {
  
  IMedia movie1 = new Movie("The Rose Garden", 1990,
                      new ConsLoString("Germany", new MtLoString()));
  IMedia movie2 = new Movie("Red and Black", 1922, new ConsLoString("Spanish", 
                      new ConsLoString("Thai", new MtLoString())));
  IMedia movie3 = new Movie("Kill the Murder", 2010,
                       new MtLoString());
  
  IMedia tvEpisode1 = new TVEpisode("The Murder", "Who is the murder of this case?", 4, 2,
                         new ConsLoString("American", new MtLoString()));
  IMedia tvEpisode2 = new TVEpisode("Romantics", "Who is she?", 1, 14,
                         new ConsLoString("Japanese", new ConsLoString("Korean",
                             new MtLoString())));
  IMedia tvEpisode3 = new TVEpisode("The Untamed", "Fight against", 1, 45,
                         new MtLoString());
  
  IMedia yTVideo1 = new YTVideo("Italian Foods' Tests", "Foods Tests", new MtLoString());
  IMedia yTVideo2 = new YTVideo("New Piano is arrived", "Piano Tests",
                       new ConsLoString("Italian", new MtLoString()));
  IMedia yTVideo3 = new YTVideo("Today we will play a popular FPS game called PUBG",
                       "FPS Games Tests", new ConsLoString("French",
                           new ConsLoString("Spanish", new MtLoString())));
  
  // test the method isCaptionAvailable
  boolean testIsCaptionAvailable(Tester t) {
    return t.checkExpect(movie1.isCaptionAvailable("French"), false)
           && t.checkExpect(tvEpisode2.isCaptionAvailable("Korean"), true)
           && t.checkExpect(yTVideo3.isCaptionAvailable("French"), true);
  }
  
  // test the method isReallyOld for Movie
  boolean testMovieIsReallyOld(Tester t) {
    return t.checkExpect(movie1.isReallyOld(), false)
           && t.checkExpect(movie2.isReallyOld(), true)
           && t.checkExpect(movie3.isReallyOld(), false);
  }
  
  // test the method isReallyOld for TVEpisode
  boolean testTVEpisodeIsReallyOld(Tester t) {
    return t.checkExpect(tvEpisode1.isReallyOld(), false)
           && t.checkExpect(tvEpisode2.isReallyOld(), false)
           && t.checkExpect(tvEpisode3.isReallyOld(), false);
  }
  
  // test the method isReallyOld for YTVideo
  boolean testyTVideoIsReallyOld(Tester t) {
    return t.checkExpect(yTVideo1.isReallyOld(), false)
           && t.checkExpect(yTVideo2.isReallyOld(), false)
           && t.checkExpect(yTVideo3.isReallyOld(), false);
  }
  
  // test the method format for Movie
  boolean testMovieFormat(Tester t) {
    return t.checkExpect(movie1.format(), "The Rose Garden (1990)")
           && t.checkExpect(movie2.format(), "Red and Black (1922)")
           && t.checkExpect(movie3.format(), "Kill the Murder (2010)");
  }
  
  // test the method format for TVEpisode
  boolean testYTVideoFormat(Tester t) {
    return t.checkExpect(tvEpisode1.format(), "Who is the murder of this case? 4.2 - The Murder")
           && t.checkExpect(tvEpisode2.format(), "Who is she? 1.14 - Romantics")
           && t.checkExpect(tvEpisode3.format(), "Fight against 1.45 - The Untamed");
  }
  
  
  // test the method format for YTVideo
  boolean testTVEpisodeFormat(Tester t) {
    return t.checkExpect(yTVideo1.format(), "Italian Foods' Tests by Foods Tests")
           && t.checkExpect(yTVideo2.format(), "New Piano is arrived by Piano Tests")
           && t.checkExpect(yTVideo3.format(),
               "Today we will play a popular FPS game called PUBG by FPS Games Tests");
  }
  
  // test the method containsCaption for ILoString
  boolean testIsContainsCaption(Tester t) {
    return t.checkExpect(new ConsLoString("Germany", 
                            new MtLoString()).containsCaption("French"), false)
           && t.checkExpect(new MtLoString().containsCaption("Korean"), false)
           && t.checkExpect(new ConsLoString("French",
               new ConsLoString("Spanish", new MtLoString())).containsCaption("French"), true);
  }
}
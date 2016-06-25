import org.apache.spark.ml.feature.{HashingTF, IDF, StopWordsRemover, Tokenizer}
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
object SparkMLLibpipeline {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "C:\\winutils")
    val spark = SparkSession
      .builder
      .appName("TfIdfExample")
      .master("local[*]")
      .getOrCreate()
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
    import spark.implicits._
    val input = spark.read.text("paper1.txt").as[String]
    val sentenceData= input.toDF("sentence")
   /* val sentenceData = spark.createDataFrame(Seq(
      (0, input)
    )).toDF("label")
*/
    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val wordsData = tokenizer.transform(sentenceData)
    val remover = new StopWordsRemover()
      .setInputCol("words")
      .setOutputCol("filteredWords")
    val processedWordData= remover.transform(wordsData)
    val hashingTF = new HashingTF()
      .setInputCol("filteredWords").setOutputCol("rawFeatures").setNumFeatures(20)
    val featurizedData = hashingTF.transform(processedWordData)
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)
    val rescaledData = idfModel.transform(featurizedData)
    //rescaledData.select("filteredWords","features", "sentence").take(10).foreach(println)
    //rescaledData.orderBy(desc("words")).take(10).foreach(println)
  val r2=rescaledData.select("words","features")
    //rescaledData.sort($"features".desc).rdd.saveAsTextFile("topTFIDF")
    //val rescaledData2=rescaledData.rdd
    //rescaledData2.sortBy(we=>we,false)
    //rescaledData.rdd.saveAsTextFile("tfidfpaper1output");
    spark.stop()
  }
}

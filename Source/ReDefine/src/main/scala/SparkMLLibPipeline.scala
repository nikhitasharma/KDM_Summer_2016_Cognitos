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
    val r2=rescaledData.select("words","features")
     spark.stop()
  }
}

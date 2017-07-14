object ws {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(55); 
  println("Welcome to the Scala worksheet");$skip(51); 
   val x=List(1).filter(_>2).minminBy((x:Int)=>x);System.out.println("""x  : <error> = """ + $show(x ));$skip(25); ;
  println("DATA = " + x);$skip(16); 
  println("xx")}
}

import java.awt.*
import javax.swing.*
import kotlin.math.*

fun main() {

    val frame = JFrame("Практична робота №2")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(600, 500)
    frame.layout = GridLayout(12, 2, 5, 5)


    val vytrataVygField = JTextField("858613.05")
    val vytrataMazytField = JTextField("88993.41")
    val vytrataGazField = JTextField("104435.26")


    val resultEmVyg = JLabel()
    val resultValVukudVyg = JLabel()
    val resultEmMazut = JLabel()
    val resultValVukudMazut = JLabel()
    val resultValVukudGaz = JLabel()

    val teplotaZgoranyaPalyvo = 20.47
    val ZolaPalyvo = 0.8
    val GorRechovynyPalyvo = 1.5
    val Efectyvnist = 0.985
    val ZolaMazut = 1.0
    val teplotaZgoranyaMazut = 39.49
    val GorRechovynMazut = 0.0
    val teplotaZgoranyaGaz = 33.08
    val pokaznukEmGaz = 0.0

    val button = JButton("Обчислити")

    button.addActionListener {

        val vVygilya = vytrataVygField.text.toDoubleOrNull() ?: 0.0
        val vMazyt = vytrataMazytField.text.toDoubleOrNull() ?: 0.0
        val vGaz = vytrataGazField.text.toDoubleOrNull() ?: 0.0

        val aPalyvo = 25.20
        val aMazut = 0.15

        val emVyg = (10.0.pow(6) / teplotaZgoranyaPalyvo) *
                ZolaPalyvo *
                (aPalyvo / (100 - GorRechovynyPalyvo)) *
                (1 - Efectyvnist)

        val valVyg = 10.0.pow(-6) * emVyg * teplotaZgoranyaPalyvo * vVygilya

        val emMazut = (10.0.pow(6) / teplotaZgoranyaMazut) *
                ZolaMazut *
                (aMazut / (100 - GorRechovynMazut)) *
                (1 - Efectyvnist)

        val valMazut = 10.0.pow(-6) * emMazut * teplotaZgoranyaMazut * vMazyt

        val valGaz = 10.0.pow(-6) * pokaznukEmGaz * teplotaZgoranyaGaz * vGaz

        resultEmVyg.text = String.format("%.2f", emVyg)
        resultValVukudVyg.text = String.format("%.2f", valVyg)
        resultEmMazut.text = String.format("%.2f", emMazut)
        resultValVukudMazut.text = String.format("%.2f", valMazut)
        resultValVukudGaz.text = String.format("%.2f", valGaz)
    }


    frame.add(JLabel("Вугілля (т):"))
    frame.add(vytrataVygField)

    frame.add(JLabel("Мазут (т):"))
    frame.add(vytrataMazytField)

    frame.add(JLabel("Газ (м³):"))
    frame.add(vytrataGazField)

    frame.add(JLabel("Показник емісії вугілля:"))
    frame.add(resultEmVyg)

    frame.add(JLabel("Валовий викид вугілля:"))
    frame.add(resultValVukudVyg)

    frame.add(JLabel("Показник емісії мазуту:"))
    frame.add(resultEmMazut)

    frame.add(JLabel("Валовий викид мазуту:"))
    frame.add(resultValVukudMazut)

    frame.add(JLabel("Валовий викид газу:"))
    frame.add(resultValVukudGaz)

    frame.add(button)

    frame.isVisible = true
}

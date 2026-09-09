package plat.lab1.composelab4.lab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//colores
private val ColorFondo = Color(0xFFF5F4FB)
private val ColorMorado = Color(0xFF4B4E8A)
private val ColorVerde = Color(0xFF2E7D32)
private val ColorRojo = Color(0xFFB33A2E)
private val ColorTexto = Color(0xFF1A1A1A)
private val ColorDivisor = Color(0xFFE0DEEA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = ColorFondo, modifier = Modifier.fillMaxSize()) {
                    ContadorScreen(nombre = "Marco Esteban Prera Roca")
                }
            }
        }
    }
}

@Composable
fun ContadorScreen(nombre: String) {

    var contador by remember { mutableStateOf(0) }
    var totalIncrementos by remember { mutableStateOf(0) }
    var totalDecrementos by remember { mutableStateOf(0) }
    var valorMaximo by remember { mutableStateOf(0) }
    var valorMinimo by remember { mutableStateOf(0) }
    var hayMovimientos by remember { mutableStateOf(false) }


    val historial = remember { mutableStateListOf<Pair<Int, Boolean>>() }

    fun registrarMovimiento(esIncremento: Boolean) {
        if (esIncremento) {
            contador++
            totalIncrementos++
        } else {
            contador--
            totalDecrementos++
        }
        if (!hayMovimientos) {

            valorMaximo = contador
            valorMinimo = contador
            hayMovimientos = true
        } else {
            if (contador > valorMaximo) valorMaximo = contador
            if (contador < valorMinimo) valorMinimo = contador
        }
        historial.add(contador to esIncremento)
    }

    fun reiniciar() {
        contador = 0
        totalIncrementos = 0
        totalDecrementos = 0
        valorMaximo = 0
        valorMinimo = 0
        hayMovimientos = false
        historial.clear()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        //scroll
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 28.dp, bottom = 16.dp)
        ) {

            Text(
                text = nombre,
                fontSize = 26.sp,
                color = ColorTexto,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BotonCircular(texto = "—", onClick = { registrarMovimiento(esIncremento = false) })

                Text(
                    text = contador.toString(),
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTexto,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier
                        .width(80.dp)
                        .padding(horizontal = 20.dp)
                )

                BotonCircular(texto = "+", onClick = { registrarMovimiento(esIncremento = true) })
            }

            Spacer(modifier = Modifier.height(24.dp))

            //Línea divisoria
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(ColorDivisor)
            )

            Spacer(modifier = Modifier.height(20.dp))

            //stats
            FilaEstadistica("Total incrementos:", totalIncrementos)
            FilaEstadistica("Total decrementos:", totalDecrementos)
            FilaEstadistica("Valor máximo:", valorMaximo)
            FilaEstadistica("Valor mínimo:", valorMinimo)
            FilaEstadistica("Total cambios:", totalIncrementos + totalDecrementos)

            Spacer(modifier = Modifier.height(4.dp))

            //historial
            Text(
                text = "Historial:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTexto
            )

            Spacer(modifier = Modifier.height(10.dp))

            HistorialGrid(historial)
        }


        Button(
            onClick = { reiniciar() },
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorMorado),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
                .height(56.dp)
        ) {
            Text(text = "Reiniciar", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
private fun BotonCircular(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = ColorMorado),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(48.dp)
    ) {
        Text(text = texto, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun FilaEstadistica(etiqueta: String, valor: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Text(
            text = etiqueta,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = ColorTexto,
            modifier = Modifier.weight(1f)
        )
        Text(text = valor.toString(), fontSize = 18.sp, color = ColorTexto)
    }
}


@Composable
private fun HistorialGrid(historial: List<Pair<Int, Boolean>>) {
    Column {
        historial.chunked(5).forEach { fila ->
            Row(modifier = Modifier.padding(bottom = 8.dp)) {
                fila.forEach { (valor, esIncremento) ->
                    ItemHistorial(valor = valor, esIncremento = esIncremento)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
private fun ItemHistorial(valor: Int, esIncremento: Boolean) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .background(
                color = if (esIncremento) ColorVerde else ColorRojo,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = valor.toString(), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
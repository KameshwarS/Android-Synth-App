package com.example.synthandroidapp


import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.synthandroidapp.ui.theme.SynthAndroidAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        enableEdgeToEdge()
        setContent {
            SynthAndroidAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    WaveTableSynth(Modifier)
                }


            }
        }
    }
}

@Composable
fun WaveTableSynth(modifier: Modifier){
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WaveTableTypePanel(modifier)
        ControlPanel(modifier)

    }
}

@Composable
fun WaveTableTypePanel(modifier: Modifier){
    Row (
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Synth Wavetable")
            WaveTableTypeButtons(modifier)
        }

    }
}

@Composable
fun WaveTableTypeButtons(modifier: Modifier){

    Row( modifier=modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,){
            for (Wavetable in arrayOf("Sine","Triangular","Square","Saw")){
                WavetableButtons(
                    modifier,
                    onClick={},
                    lable=Wavetable)
            }

    }
}
@Composable
fun WavetableButtons(modifier: Modifier, onClick: () -> Unit, lable: String) {
    Button(modifier=modifier, onClick = onClick) {
        Text(lable)
    }

}

@Composable
fun ControlPanel(modifier: Modifier){
    Row (
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier= modifier
                .fillMaxHeight()
                .fillMaxWidth(0.7f) ,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            PitchControl(modifier)
            PlayControl(modifier)

        }
        Column(
            modifier= modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VolumeControl(modifier)
        }
    }
}
@Composable
fun PitchControl(modifier: Modifier){

    val freq= rememberSaveable{mutableStateOf(300F
    )}



    PitchControlContent(modifier,
        pitchControlLabel= stringResource(R.string.frequency),
        value=freq.value,
        onValueChange={
            freq.value=it
        },
        valueRange=40F..3000F,
        freqValueLabel=stringResource(R.string.frequency,freq.value)
    )
}

@Composable
fun PitchControlContent(modifier: Modifier,pitchControlLabel: String,
                        value: Float,
                        onValueChange: (Float) -> Unit,
                        valueRange: ClosedFloatingPointRange<Float>,
                        freqValueLabel: String){


    Text(stringResource(R.string.frequency))
    Slider( modifier=modifier, value = value, onValueChange = onValueChange, valueRange = valueRange)
    Text(value.toString())
}


@Composable
fun PlayControl(modifier: Modifier){
    Button(modifier=modifier,onClick={}) {
        Text("Play")

    }

}

@Composable
fun VolumeControl(modifier: Modifier){

    val screenH= LocalConfiguration.current.screenHeightDp
    val sliderW=screenH/4

    val vol= rememberSaveable{mutableStateOf(-10F)}


    VolumeControlContent(modifier = modifier,value=vol.value,onValueChange={vol.value=it},
        volumeRange = -60F..0F
    )
}

@Composable
fun VolumeControlContent(modifier: Modifier,
                         value: Float,
                        onValueChange: (Float) -> Unit,
                         volumeRange:ClosedFloatingPointRange<Float>){

    Icon(imageVector = Icons.Filled.VolumeUp,contentDescription = null)

    val screenH= LocalConfiguration.current.screenHeightDp
    val sliderW=screenH/4


    Slider(
        value = value,
        onValueChange = onValueChange,
        modifier= modifier
            .width(sliderW.dp)
            .rotate(270F),
        valueRange = volumeRange
    )

    Icon(imageVector = Icons.Filled.VolumeMute,contentDescription = null)

}
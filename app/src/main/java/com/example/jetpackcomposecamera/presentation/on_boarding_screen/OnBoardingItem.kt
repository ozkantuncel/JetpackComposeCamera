package com.example.jetpackcomposecamera.presentation.on_boarding_screen

import com.example.jetpackcomposecamera.R

sealed class OnBoardingItem(
    val image: Int,
    val title: String,
) {
    object First : OnBoardingItem(
        image = R.drawable.rm_one,
        title = "Gerçeklik subjektiftir. Bizim bildiğimiz tek şey, bizim algıladığımız şeydir",
    )
    object Second : OnBoardingItem(
        image = R.drawable.rm_two,
        title = "Kimse amaç için var olmaz, kimse hiçbir yere ait değildir, herkes ölecek. Gel TV izleyelim.",
    )
    object Third : OnBoardingItem(
        image = R.drawable.rm_three,
        title = "Biraz macera istiyorsan, bana katıl. Ama dikkatli ol, tehlikeli olabilir.",
    )
}

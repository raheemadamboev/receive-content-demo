package xyz.teamgravity.receivecontentdemo

import android.app.Application
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

class App : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this)
            .newBuilder()
            .components {
                add(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) ImageDecoderDecoder.Factory() else GifDecoder.Factory())
            }.build()
    }
}
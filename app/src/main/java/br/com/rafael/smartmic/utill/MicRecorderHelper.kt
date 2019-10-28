package br.com.rafael.smartmic.utill

import android.media.AudioFormat
import com.github.piasy.rxandroidaudio.StreamAudioRecorder
import com.github.piasy.rxandroidaudio.StreamAudioRecorder.DEFAULT_SAMPLE_RATE
import rx.subjects.PublishSubject

/**
 * Created by Santander on 28/10/2019
 */
class MicRecorderHelper(private val audioRecorder: StreamAudioRecorder) :
    StreamAudioRecorder.AudioDataCallback {

    private val observable: PublishSubject<ByteArray?> by lazy { PublishSubject.create<ByteArray>() }

    fun start(): PublishSubject<ByteArray?> {
        audioRecorder.start(
            SAMPLE_RATE, CHANNEL,
            ENCODING, BUFFER_SIZE, this
        )

        return observable
    }

    fun stop() {
        audioRecorder.stop()
    }

    override fun onError() {
        observable.onError(RuntimeException())
    }

    override fun onAudioData(data: ByteArray?, size: Int) {
        observable.onNext(data)
    }


    companion object {
        const val BUFFER_SIZE = 1024
        const val SAMPLE_RATE = DEFAULT_SAMPLE_RATE
        const val CHANNEL = AudioFormat.CHANNEL_IN_MONO
        const val ENCODING = AudioFormat.ENCODING_PCM_16BIT
    }

}
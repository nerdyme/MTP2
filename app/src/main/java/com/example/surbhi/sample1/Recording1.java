package com.example.surbhi.sample1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Recording1 extends BaseActionbar {

    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder recorder = null;
    private int currentFormat = 0;
    private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP };
    private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP };
    private String myfile=null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording1);

        setButtonHandlers();

        enableButtons(false);

        setFormatButtonCaption();

    }

    private void setButtonHandlers() {
        ((Button) findViewById(R.id.btnStart)).setOnClickListener(btnClick);
        ((Button) findViewById(R.id.btnStop)).setOnClickListener(btnClick);
        ((Button) findViewById(R.id.btnFormat)).setOnClickListener(btnClick);
    }

    private void enableButton(int id, boolean isEnable) {
        ((Button) findViewById(id)).setEnabled(isEnable);
    }

    private void enableButtons(boolean isRecording) {
        enableButton(R.id.btnStart, !isRecording);
        enableButton(R.id.btnFormat, !isRecording);
        enableButton(R.id.btnStop, isRecording);
    }

    private void setFormatButtonCaption() {
        ((Button) findViewById(R.id.btnFormat)).setText(getString(R.string.audio_format) + " (" + file_exts[currentFormat] + ")");
    }

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);
        if (!file.exists()) {
            file.mkdirs();
        }
        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
    }

    private void startRecording() {

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        myfile = getFilename();
        recorder.setOutputFile(myfile);
        Log.w("filename",getFilename());
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if ( recorder!=null) {
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;

            Intent i = new Intent(getApplicationContext(),Recording2.class).putExtra("filename",myfile);
            startActivity(i);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error in Loading Recorder",Toast.LENGTH_SHORT ).show();
        }

    }

    private void displayFormatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String formats[] = { "MPEG 4", "3GP" };
        builder.setTitle(getString(R.string.choose_format_title)).setSingleChoiceItems(formats, currentFormat, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentFormat = which;
                setFormatButtonCaption();
                dialog.dismiss();
            }
        }).show();
    }

    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            Toast.makeText(Recording1.this, "Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
        }
    };

    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            Toast.makeText(Recording1.this, "Warning: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btnStart: {
                    Toast.makeText(Recording1.this, "Start Recording", Toast.LENGTH_SHORT).show();
                    enableButtons(true);
                    startRecording();
                    break;
                }
                case R.id.btnStop: {
                    Toast.makeText(Recording1.this, "Stop Recording", Toast.LENGTH_SHORT).show();
                    enableButtons(false);
                    stopRecording();
                    break;
                }
                case R.id.btnFormat: {
                    displayFormatDialog();
                    break;
                }
            }
        }
    };


}

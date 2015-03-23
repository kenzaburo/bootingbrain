package com.academic.idiots.bootingbrain;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.academic.idiots.bootingbrain.matchingpictures.GameManager;
import com.academic.idiots.bootingbrain.matchingpictures.LevelInfo;
import com.academic.idiots.bootingbrain.matchingpictures.Player;

import java.util.Random;

public class MatchingPictureActivity extends Activity implements OnClickListener, GameManager.EndGameListenner {

    private Context mContext = this;
    private Animation animation_to_middle;
    private Animation animation_from_middle;
    public com.wefika.flowlayout.FlowLayout flowLayout;
    private boolean isBackOfCardShowing = true;

    private boolean onePersonMod = true;
    private TextView tvScore;
    private TextView tvName;
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_picture);
        animation_to_middle = AnimationUtils.loadAnimation(this,
                R.anim.to_middle);

//		animation_to_middle.setAnimationListener(this);
        animation_from_middle = AnimationUtils.loadAnimation(this,
                R.anim.from_middle);

//		animation_from_middle.setAnimationListener(this);
        flowLayout = (com.wefika.flowlayout.FlowLayout) findViewById(R.id.flow_layout_id);

        gameManager = new GameManager();
        gameManager.setEndGameListenner(this);
        if (onePersonMod) {
            gameManager.addPlayer(new Player(0, "Thien"));
        }
        newGame();
        tvScore = (TextView) findViewById(R.id.tv_sore);
        tvScore.setText(gameManager.getCurrentPlayer().getScore() + "");
        tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(gameManager.getCurrentPlayer().getName());
    }

    public void newGame() {
        LevelInfo levelInfo = gameManager.getLevelInfo();
        removeAllViews();
        addViews(levelInfo.getHeightSize() * levelInfo.getWidthSize());
    }


    public static final int[] R_ID = {
            R.drawable.abra_1
            , R.drawable.carterpie_1
            , R.drawable.raichu_1
            , R.drawable.ala_1
            , R.drawable.champ_1
            , R.drawable.genga_1
            , R.drawable.sqrtle_1
            , R.drawable.arcanie_1
            , R.drawable.char_1
            , R.drawable.growl_1
            , R.drawable.starmine_1
            , R.drawable.charizar_1
            , R.drawable.vena_1
            , R.drawable.blast_1
            , R.drawable.charm_1
            , R.drawable.ivy_1
            , R.drawable.vile_1
            , R.drawable.bulba_1
            , R.drawable.cubone_1
            , R.drawable.king_1
            , R.drawable.water_1
            , R.drawable.butter_1
            , R.drawable.dragon_1
            , R.drawable.moth_1
            , R.drawable.dragonite
            , R.drawable.onix_1
            , R.drawable.dratini_1
            , R.drawable.pika_1
    };

    PictureHolder[] pictureHolders;

    public void removeAllViews() {
        flowLayout.removeAllViews();
    }

    public void addViews(int size) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        int length = Math.min(size, R_ID.length * 2);
        pictureHolders = new PictureHolder[length];
        Random random = new Random();
        int rIDs[] = new int[length];
        int haft = length / 2;
        for (int i = 0; i < haft; i++) {
            rIDs[i] = R_ID[i];
            rIDs[i + haft] = R_ID[i];
        }
        for (int i = (length - 1); i >= 0; i--) {
            int randInt = random.nextInt(i + 1);
            // swap code
            int temp = rIDs[i];
            rIDs[i] = rIDs[randInt];
            rIDs[randInt] = temp;

            PictureMdl pictureMdl = new PictureMdl(rIDs[i]);
            ImageView v = (ImageView) layoutInflater.inflate(R.layout.picture, flowLayout, false);
            pictureHolders[i] = new PictureHolder(pictureMdl, v);
            pictureHolders[i].setOnImageClickListener(new ImageListener(pictureHolders[i]));
            flowLayout.addView(v);
        }
    }


    int active = 0;
    PictureHolder firstActive;
    PictureHolder secondActive;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onEndGame() {
        gameManager.levelUp();
        newGame();
    }

    class ImageListener implements OnClickListener {
        PictureHolder mPictureHolder;

        public ImageListener(PictureHolder pictureHolder) {
            mPictureHolder = pictureHolder;
        }

        @Override
        public void onClick(View view) {
            if (!mPictureHolder.isEnable()) {
                return;
            }

            if (active == 2) {
                return;
            }

            mPictureHolder.flip();
            if (active == 1) {

                if (firstActive != mPictureHolder) {
                    secondActive = mPictureHolder;
                    active = 2;
                    android.os.Handler handler = new android.os.Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (firstActive.model.mR_ID == secondActive.model.mR_ID) {
                                firstActive.setEnable(false);
                                secondActive.setEnable(false);
                                firstActive.imageView.setImageResource(R.drawable.transparent);
                                secondActive.imageView.setImageResource(R.drawable.transparent);
                                gameManager.increaseMatching();
                                tvScore.setText(gameManager.getCurrentPlayer().getScore() + "");
                            } else {
                                firstActive.flip();
                                secondActive.flip();
                            }
                            active = 0;
                        }
                    }, 700);
                    return;
                } else {
                    active = 0;
                    return;
                }
            }
            if (active == 0) {
                firstActive = mPictureHolder;
                active = 1;
                return;
            }
        }
    }

    class PictureMdl {
        public int mR_ID;
        boolean isFont = false;

        public PictureMdl(int R_ID) {
            this.mR_ID = R_ID;
        }
    }

    class PictureHolder {
        public PictureMdl model;
        public ImageView imageView;
        private boolean enable = true;
        Animation anim_to_middle, anim_from_middle;

        public PictureHolder(PictureMdl model, ImageView imageView) {
            this.model = model;
            this.imageView = imageView;
            init();
        }

        /**
         * set OnClickListener for Image
         *
         * @param onClickListener
         */
        public void setOnImageClickListener(OnClickListener onClickListener) {
            imageView.setOnClickListener(onClickListener);
        }

        private void init() {
            this.imageView.setImageResource(R.drawable.back_of_card);
            anim_to_middle = AnimationUtils.loadAnimation(mContext,
                    R.anim.to_middle);
            anim_from_middle = AnimationUtils.loadAnimation(mContext,
                    R.anim.from_middle);

            anim_to_middle.setAnimationListener(new AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    Log.i("onAnimation", "Start " + animation.getClass().getName());
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.i("onAnimation", "End " + animation.getClass().getName());
                    imageView.clearAnimation();
                    if (model.isFont) {
                        imageView.setImageResource(R.drawable.back_of_card);
                        model.isFont = false;
                    } else {
                        imageView.setImageResource(model.mR_ID);
                        model.isFont = true;
                    }
                    imageView.clearAnimation();
                    imageView.startAnimation(anim_from_middle);
                }
            });

            anim_from_middle.setAnimationListener(new AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub
                    Log.i("onAnimation", "Start " + animation.getClass().getName());
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.i("onAnimation", "End " + animation.getClass().getName());
                    if (flipListener != null) {
                        flipListener.onEndFlip(model, imageView);
                    }
                    setEnable(true);
                }
            });
        }

        private FlipListener flipListener = null;

        public void setFlipListener(FlipListener flipListener) {
            this.flipListener = flipListener;
        }

        public void flip() {
            setEnable(false);
            if (flipListener != null) {
                flipListener.onStartFlip(model, imageView);
            }
            imageView.clearAnimation();
            imageView.startAnimation(anim_to_middle);
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean isEnable) {
            this.enable = isEnable;
        }
    }

    interface FlipListener {
        public void onStartFlip(PictureMdl pictureMdl, ImageView imageView);

        public void onEndFlip(PictureMdl pictureMdl, ImageView imageView);
    }
}

package ru.sandroid.appiumeducations;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

@AndroidFindBy(id = "zen_ribbon_image_card")
public class ZenCard extends AndroidElement {

        //Картинка карточки
        @AndroidFindBy(id = "zen_image")
        public AndroidElement zenCardImage;

        //Название сайта
        @AndroidFindBy(id = "zen_site_name")
        public AndroidElement zenSiteName;

        //Кнопка отзыва
        @AndroidFindBy(id = "zen_feedback")
        public AndroidElement zenFeedbackButton;

        //Тайтл и текст карточки
        @AndroidFindBy(id = "zen_title_and_body")
        public AndroidElement zenTitleAndBody;

}



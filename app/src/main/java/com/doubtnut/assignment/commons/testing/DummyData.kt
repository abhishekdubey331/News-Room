package com.doubtnut.assignment.commons.testing

import androidx.annotation.VisibleForTesting
import com.doubtnut.assignment.commons.data.NewsArticle
import com.doubtnut.assignment.commons.data.NewsStory
import com.doubtnut.assignment.commons.data.Source


@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object DummyData {

    fun getNewsStory() = NewsStory(listOf(NewsArticle(
            "Igor Bonifacic",
            "",
            "",
            "2020-03-14T11:04:08Z",
            Source("engadget", "Engadget"), "Pixel 4a will reportedly feature faster UFS 2.1 storage - Engadget",
            "https://www.engadget.com/2020/03/14/pixel-4a-faster-ufs-storage/",
            "https://o.aolcdn.com/images/dims?resize=1200%2C630&crop=1200%2C630%2C0%2C0&quality=80&image_uri=https%3A%2F%2Fo.aolcdn.com%2Fimages%2Fdims%3Fresize%3D2000%252C2000%252Cshrink%26image_uri%3Dhttps%253A%252F%252Fs.yimg.com%252Fos%252Fcreatr-uploaded-images%252F2020-03%252F98f3f6a0-62fb-11ea-bb7f-d6a22f5084b1%26client%3Da1acac3e1b3290917d92%26signature%3Ddc4090f067028f0744f7cf60161787e0261f430d&client=amp-blogside-v2&signature=ca0f9dfe0e58162b1cfc371e4a45e81884bb621f")),
            "ok", 2)
}
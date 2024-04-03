package tech.lyuku.englishmanual.feature.books.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import tech.lyuku.englishmanual.books.R

enum class BookDetailAction(
    val id: Int,
    @StringRes val actionName: Int,
    @DrawableRes val icon: Int
) {
    ACTION_APP_STUDY(0, R.string.book_action_study, R.drawable.icon_study_quiz),

    ACTION_TEST(1, R.string.book_action_test, R.drawable.icon_study_test),

    ACTION_LISTENING(2, R.string.book_action_audio, R.drawable.icon_study_audio),

    ACTION_SHADOWING(3, R.string.book_action_shadowing, R.drawable.icon_s_w),

    ACTION_VOCABULARY(4, R.string.book_action_vocab, R.drawable.icon_vocab),

    ACTION_MARKEDLIST(5, R.string.book_action_mark_sheet, R.drawable.icon_study_marksheet),

    ACTION_STUDYTIME(6, R.string.book_action_study_time, R.drawable.icon_study_record)
}

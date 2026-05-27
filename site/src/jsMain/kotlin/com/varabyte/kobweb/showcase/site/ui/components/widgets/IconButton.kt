package com.varabyte.kobweb.showcase.site.ui.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.setVariable
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.ButtonVars
import org.jetbrains.compose.web.css.em
import com.varabyte.kobweb.showcase.site.ui.styles.CircleButtonVariant
import com.varabyte.kobweb.showcase.site.ui.styles.UncoloredButtonVariant

@Composable
fun IconButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    Button(
        onClick = { onClick() },
        Modifier.setVariable(ButtonVars.FontSize, 1.em),
        variant = CircleButtonVariant.then(
            UncoloredButtonVariant
        )
    ) {
        content()
    }
}

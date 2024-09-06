package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class RepresentationTest {
    @Test
    fun `representation with null properties`() {
        val representation = Representation(
            name = "representation-name",
            properties = null,
        )

        assertNull(representation.title)
        assertNull(representation.description)
        assertNull(representation.label)
        assertFalse(representation.hasDetailPage)
        assertFalse(representation.pickRandomElement)
        assertNull(representation.imageUrl)
        assertNull(representation.imageFocalPoint)
        assertNull(representation.link)
    }

    @Test
    fun `representation with empty properties`() {
        val representation = Representation(
            name = "representation-name",
            properties = Representation.Properties(),
        )

        assertNull(representation.title)
        assertNull(representation.description)
        assertNull(representation.label)
        assertFalse(representation.hasDetailPage)
        assertFalse(representation.pickRandomElement)
        assertNull(representation.imageUrl)
        assertNull(representation.imageFocalPoint)
        assertNull(representation.link)
    }

    @Test
    fun `representation with properties`() {
        val properties = Representation.Properties(
            title = "title",
            description = "description",
            label = "label",
            hasDetailPage = true,
            pickRandomElement = true,
            imageUrl = ImageUrl("https://image.url/image.jpg"),
            imageFocalPoint = FocalPoint(25, 75),
            link = Link(
                targetType = "target-type",
                target = "target",
            ),
        )
        val representation = Representation(
            name = "representation-name",
            properties = properties,
        )

        assertEquals(properties.title, representation.title)
        assertEquals(properties.description, representation.description)
        assertEquals(properties.label, representation.label)
        assertEquals(properties.hasDetailPage, representation.hasDetailPage)
        assertEquals(properties.pickRandomElement, representation.pickRandomElement)
        assertEquals(properties.imageUrl, representation.imageUrl)
        assertEquals(properties.imageFocalPoint, representation.imageFocalPoint)
        assertEquals(properties.link, representation.link)
    }
}

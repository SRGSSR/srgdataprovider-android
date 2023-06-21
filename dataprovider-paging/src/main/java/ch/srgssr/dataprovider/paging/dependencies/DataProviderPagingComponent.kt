package ch.srgssr.dataprovider.paging.dependencies

import ch.srg.dataProvider.integrationlayer.dependencies.components.IlDataProviderComponent
import ch.srgssr.dataprovider.paging.DataProviderPaging
import dagger.Component

@DataProviderPagingScope
@Component(dependencies = [IlDataProviderComponent::class])
interface DataProviderPagingComponent {

    val dataProviderPaging: DataProviderPaging
}

object DataProviderPagingDependencies {
    fun create(ilDataProviderComponent: IlDataProviderComponent): DataProviderPagingComponent {
        return DaggerDataProviderPagingComponent.builder()
            .ilDataProviderComponent(ilDataProviderComponent)
            .build()
    }
}

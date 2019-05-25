import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'points',
                loadChildren: './points/points.module#TwentyOnePointsPointsModule'
            },
            {
                path: 'goal-settings',
                loadChildren: './goal-settings/goal-settings.module#TwentyOnePointsGoalSettingsModule'
            },
            {
                path: 'goal-settings',
                loadChildren: './goal-settings/goal-settings.module#TwentyOnePointsGoalSettingsModule'
            },
            {
                path: 'weight',
                loadChildren: './weight/weight.module#TwentyOnePointsWeightModule'
            },
            {
                path: 'weight',
                loadChildren: './weight/weight.module#TwentyOnePointsWeightModule'
            },
            {
                path: 'blood-pressure',
                loadChildren: './blood-pressure/blood-pressure.module#TwentyOnePointsBloodPressureModule'
            },
            {
                path: 'blood-pressure',
                loadChildren: './blood-pressure/blood-pressure.module#TwentyOnePointsBloodPressureModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TwentyOnePointsEntityModule {}

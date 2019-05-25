import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TwentyOnePointsSharedModule } from 'app/shared';
import {
    GoalSettingsComponent,
    GoalSettingsDetailComponent,
    GoalSettingsUpdateComponent,
    GoalSettingsDeletePopupComponent,
    GoalSettingsDeleteDialogComponent,
    goalSettingsRoute,
    goalSettingsPopupRoute
} from './';

const ENTITY_STATES = [...goalSettingsRoute, ...goalSettingsPopupRoute];

@NgModule({
    imports: [TwentyOnePointsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GoalSettingsComponent,
        GoalSettingsDetailComponent,
        GoalSettingsUpdateComponent,
        GoalSettingsDeleteDialogComponent,
        GoalSettingsDeletePopupComponent
    ],
    entryComponents: [
        GoalSettingsComponent,
        GoalSettingsUpdateComponent,
        GoalSettingsDeleteDialogComponent,
        GoalSettingsDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TwentyOnePointsGoalSettingsModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}

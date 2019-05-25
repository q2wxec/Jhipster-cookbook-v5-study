import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoalSettings } from 'app/shared/model/goal-settings.model';

@Component({
    selector: 'jhi-goal-settings-detail',
    templateUrl: './goal-settings-detail.component.html'
})
export class GoalSettingsDetailComponent implements OnInit {
    goalSettings: IGoalSettings;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ goalSettings }) => {
            this.goalSettings = goalSettings;
        });
    }

    previousState() {
        window.history.back();
    }
}

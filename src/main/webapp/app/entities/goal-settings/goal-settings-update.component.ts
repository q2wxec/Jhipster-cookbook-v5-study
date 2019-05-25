import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IGoalSettings } from 'app/shared/model/goal-settings.model';
import { GoalSettingsService } from './goal-settings.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-goal-settings-update',
    templateUrl: './goal-settings-update.component.html'
})
export class GoalSettingsUpdateComponent implements OnInit {
    goalSettings: IGoalSettings;
    isSaving: boolean;

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected goalSettingsService: GoalSettingsService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ goalSettings }) => {
            this.goalSettings = goalSettings;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.goalSettings.id !== undefined) {
            this.subscribeToSaveResponse(this.goalSettingsService.update(this.goalSettings));
        } else {
            this.subscribeToSaveResponse(this.goalSettingsService.create(this.goalSettings));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoalSettings>>) {
        result.subscribe((res: HttpResponse<IGoalSettings>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}

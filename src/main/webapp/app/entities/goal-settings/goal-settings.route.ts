import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { GoalSettings } from 'app/shared/model/goal-settings.model';
import { GoalSettingsService } from './goal-settings.service';
import { GoalSettingsComponent } from './goal-settings.component';
import { GoalSettingsDetailComponent } from './goal-settings-detail.component';
import { GoalSettingsUpdateComponent } from './goal-settings-update.component';
import { GoalSettingsDeletePopupComponent } from './goal-settings-delete-dialog.component';
import { IGoalSettings } from 'app/shared/model/goal-settings.model';

@Injectable({ providedIn: 'root' })
export class GoalSettingsResolve implements Resolve<IGoalSettings> {
    constructor(private service: GoalSettingsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IGoalSettings> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<GoalSettings>) => response.ok),
                map((goalSettings: HttpResponse<GoalSettings>) => goalSettings.body)
            );
        }
        return of(new GoalSettings());
    }
}

export const goalSettingsRoute: Routes = [
    {
        path: '',
        component: GoalSettingsComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'twentyOnePointsApp.goalSettings.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: GoalSettingsDetailComponent,
        resolve: {
            goalSettings: GoalSettingsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'twentyOnePointsApp.goalSettings.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: GoalSettingsUpdateComponent,
        resolve: {
            goalSettings: GoalSettingsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'twentyOnePointsApp.goalSettings.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: GoalSettingsUpdateComponent,
        resolve: {
            goalSettings: GoalSettingsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'twentyOnePointsApp.goalSettings.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const goalSettingsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: GoalSettingsDeletePopupComponent,
        resolve: {
            goalSettings: GoalSettingsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'twentyOnePointsApp.goalSettings.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

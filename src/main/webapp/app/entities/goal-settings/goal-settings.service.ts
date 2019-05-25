import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGoalSettings } from 'app/shared/model/goal-settings.model';

type EntityResponseType = HttpResponse<IGoalSettings>;
type EntityArrayResponseType = HttpResponse<IGoalSettings[]>;

@Injectable({ providedIn: 'root' })
export class GoalSettingsService {
    public resourceUrl = SERVER_API_URL + 'api/goal-settings';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/goal-settings';

    constructor(protected http: HttpClient) {}

    create(goalSettings: IGoalSettings): Observable<EntityResponseType> {
        return this.http.post<IGoalSettings>(this.resourceUrl, goalSettings, { observe: 'response' });
    }

    update(goalSettings: IGoalSettings): Observable<EntityResponseType> {
        return this.http.put<IGoalSettings>(this.resourceUrl, goalSettings, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGoalSettings>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGoalSettings[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGoalSettings[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}

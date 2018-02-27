import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Goal } from './goal.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class GoalService {

    private resourceUrl = SERVER_API_URL + 'api/goals';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(goal: Goal): Observable<Goal> {
        const copy = this.convert(goal);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(goal: Goal): Observable<Goal> {
        const copy = this.convert(goal);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Goal> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Goal.
     */
    private convertItemFromServer(json: any): Goal {
        const entity: Goal = Object.assign(new Goal(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a Goal to a JSON which can be sent to the server.
     */
    private convert(goal: Goal): Goal {
        const copy: Goal = Object.assign({}, goal);

        copy.date = this.dateUtils.toDate(goal.date);
        return copy;
    }
}

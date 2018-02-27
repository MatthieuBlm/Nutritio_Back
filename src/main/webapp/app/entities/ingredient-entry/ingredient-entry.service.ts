import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { IngredientEntry } from './ingredient-entry.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class IngredientEntryService {

    private resourceUrl = SERVER_API_URL + 'api/ingredient-entries';

    constructor(private http: Http) { }

    create(ingredientEntry: IngredientEntry): Observable<IngredientEntry> {
        const copy = this.convert(ingredientEntry);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(ingredientEntry: IngredientEntry): Observable<IngredientEntry> {
        const copy = this.convert(ingredientEntry);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<IngredientEntry> {
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
     * Convert a returned JSON object to IngredientEntry.
     */
    private convertItemFromServer(json: any): IngredientEntry {
        const entity: IngredientEntry = Object.assign(new IngredientEntry(), json);
        return entity;
    }

    /**
     * Convert a IngredientEntry to a JSON which can be sent to the server.
     */
    private convert(ingredientEntry: IngredientEntry): IngredientEntry {
        const copy: IngredientEntry = Object.assign({}, ingredientEntry);
        return copy;
    }
}

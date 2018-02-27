import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Meal } from './meal.model';
import { MealService } from './meal.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-meal',
    templateUrl: './meal.component.html'
})
export class MealComponent implements OnInit, OnDestroy {
meals: Meal[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mealService: MealService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.mealService.query().subscribe(
            (res: ResponseWrapper) => {
                this.meals = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMeals();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Meal) {
        return item.id;
    }
    registerChangeInMeals() {
        this.eventSubscriber = this.eventManager.subscribe('mealListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

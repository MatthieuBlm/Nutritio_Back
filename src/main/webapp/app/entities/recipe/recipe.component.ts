import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Recipe } from './recipe.model';
import { RecipeService } from './recipe.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-recipe',
    templateUrl: './recipe.component.html'
})
export class RecipeComponent implements OnInit, OnDestroy {
recipes: Recipe[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private recipeService: RecipeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.recipeService.query().subscribe(
            (res: ResponseWrapper) => {
                this.recipes = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRecipes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Recipe) {
        return item.id;
    }
    registerChangeInRecipes() {
        this.eventSubscriber = this.eventManager.subscribe('recipeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { IngredientEntry } from './ingredient-entry.model';
import { IngredientEntryService } from './ingredient-entry.service';

@Component({
    selector: 'jhi-ingredient-entry-detail',
    templateUrl: './ingredient-entry-detail.component.html'
})
export class IngredientEntryDetailComponent implements OnInit, OnDestroy {

    ingredientEntry: IngredientEntry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ingredientEntryService: IngredientEntryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIngredientEntries();
    }

    load(id) {
        this.ingredientEntryService.find(id).subscribe((ingredientEntry) => {
            this.ingredientEntry = ingredientEntry;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIngredientEntries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ingredientEntryListModification',
            (response) => this.load(this.ingredientEntry.id)
        );
    }
}

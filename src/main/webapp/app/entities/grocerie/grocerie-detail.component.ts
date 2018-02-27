import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Grocerie } from './grocerie.model';
import { GrocerieService } from './grocerie.service';

@Component({
    selector: 'jhi-grocerie-detail',
    templateUrl: './grocerie-detail.component.html'
})
export class GrocerieDetailComponent implements OnInit, OnDestroy {

    grocerie: Grocerie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private grocerieService: GrocerieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGroceries();
    }

    load(id) {
        this.grocerieService.find(id).subscribe((grocerie) => {
            this.grocerie = grocerie;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGroceries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'grocerieListModification',
            (response) => this.load(this.grocerie.id)
        );
    }
}

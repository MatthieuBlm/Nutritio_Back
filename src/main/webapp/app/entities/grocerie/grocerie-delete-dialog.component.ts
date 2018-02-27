import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Grocerie } from './grocerie.model';
import { GroceriePopupService } from './grocerie-popup.service';
import { GrocerieService } from './grocerie.service';

@Component({
    selector: 'jhi-grocerie-delete-dialog',
    templateUrl: './grocerie-delete-dialog.component.html'
})
export class GrocerieDeleteDialogComponent {

    grocerie: Grocerie;

    constructor(
        private grocerieService: GrocerieService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.grocerieService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'grocerieListModification',
                content: 'Deleted an grocerie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-grocerie-delete-popup',
    template: ''
})
export class GrocerieDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private groceriePopupService: GroceriePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.groceriePopupService
                .open(GrocerieDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

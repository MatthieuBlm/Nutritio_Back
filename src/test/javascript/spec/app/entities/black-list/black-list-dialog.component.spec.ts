/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { NutritioBackTestModule } from '../../../test.module';
import { BlackListDialogComponent } from '../../../../../../main/webapp/app/entities/black-list/black-list-dialog.component';
import { BlackListService } from '../../../../../../main/webapp/app/entities/black-list/black-list.service';
import { BlackList } from '../../../../../../main/webapp/app/entities/black-list/black-list.model';
import { IngredientEntryService } from '../../../../../../main/webapp/app/entities/ingredient-entry';

describe('Component Tests', () => {

    describe('BlackList Management Dialog Component', () => {
        let comp: BlackListDialogComponent;
        let fixture: ComponentFixture<BlackListDialogComponent>;
        let service: BlackListService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NutritioBackTestModule],
                declarations: [BlackListDialogComponent],
                providers: [
                    IngredientEntryService,
                    BlackListService
                ]
            })
            .overrideTemplate(BlackListDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BlackListDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BlackListService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BlackList(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.blackList = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'blackListListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BlackList();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.blackList = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'blackListListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

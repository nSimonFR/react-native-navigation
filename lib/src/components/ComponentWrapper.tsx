import * as React from 'react';
import * as  _ from 'lodash';
import * as ReactLifecyclesCompat from 'react-lifecycles-compat';

export class ComponentWrapper {

  static wrap(componentName: string, OriginalComponentClass: React.ComponentType<any>, store,
              reduxStore?: object, ReduxProvider?: React.ComponentType<any>, reduxOptions?: object): React.ComponentType<any> {

    class WrappedComponent extends React.Component<any, { componentId: string; allProps: {}; }> {

      static getDerivedStateFromProps(nextProps, prevState) {
        return {
          allProps: _.merge({}, nextProps, store.getPropsForId(prevState.componentId))
        };
      }

      private originalComponentRef;

      constructor(props) {
        super(props);
        this._assertComponentId();
        this._saveComponentRef = this._saveComponentRef.bind(this);
        this.state = {
          componentId: props.componentId,
          allProps: {}
        };
      }

      componentDidMount() {
        store.setRefForId(this.state.componentId, this);
      }

      componentWillUnmount() {
        store.cleanId(this.state.componentId);
      }

      componentDidAppear() {
        if (this.originalComponentRef.componentDidAppear) {
          this.originalComponentRef.componentDidAppear();
        }
      }

      componentDidDisappear() {
        if (this.originalComponentRef.componentDidDisappear) {
          this.originalComponentRef.componentDidDisappear();
        }
      }

      onNavigationButtonPressed(buttonId) {
        if (this.originalComponentRef.onNavigationButtonPressed) {
          this.originalComponentRef.onNavigationButtonPressed(buttonId);
        }
      }

      render() {
        const ComponentWrapped = (
          <OriginalComponentClass
            ref={this._saveComponentRef}
            {...this.state.allProps}
            componentId={this.state.componentId}
            key={this.state.componentId}
          />
        );

        if (reduxStore && ReduxProvider) {
          return (
            <ReduxProvider store={reduxStore} {...reduxOptions}>
              {WrappedComponent}
            </ReduxProvider>
          );
        } else {
          return WrappedComponent;
        }
      }

      private _assertComponentId() {
        if (!this.props.componentId) {
          throw new Error(`Component ${componentName} does not have a componentId!`);
        }
      }

      private _saveComponentRef(r) {
        if (reduxStore && ReduxProvider && r.getWrappedInstance) {
          this.originalComponentRef = r.getWrappedInstance();
        } else {
          this.originalComponentRef = r;
        }
      }
    }

    ReactLifecyclesCompat.polyfill(WrappedComponent);

    return WrappedComponent;
  }
}
